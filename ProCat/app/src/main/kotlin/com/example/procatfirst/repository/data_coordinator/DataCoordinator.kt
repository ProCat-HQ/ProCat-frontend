package com.example.procatfirst.repository.data_coordinator

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.datastore.preferences.protobuf.Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.UserCartCache
import com.example.procatfirst.repository.data_storage.DataStorage
import com.example.procatfirst.repository.data_storage.getRefresh
import com.example.procatfirst.repository.data_storage.setRefresh
import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD
import com.example.procatfirst.repository.data_storage_deprecated.getUserEmailDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.cert.Certificate
import java.security.cert.CertificateEncodingException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.Locale

/**
 * К этому (и только к этому) статическому классу обращаемся
 * для получения или изменения данных из кэша/памяти/сервера (всего)
 *
 */
class DataCoordinator {

    companion object {
        val shared = DataCoordinator()
        const val identifier = "[DataCoordinator]"
    }

    private var fingerprint = ""

    suspend fun initialize(con: Context) {
        DataStorage.shared.initialize(con)
        fingerprint = setFingerPrint(con)

        //Чтобы корзина работала до её открытия (когда добавляем инструмент)
        withContext(Dispatchers.IO) {
            UserCartCache.shared.setUserCartStuff(DataStorage.shared.getUserCartFromMemory())
        }
    }

    suspend fun refresh(callback : (String, String, String) -> Unit, context: Context) {
        //val refresh = DataStorage.shared.getRefresh()
        val refresh = DataCoordinatorOLD.shared.getUserEmailDataStore(context)
        if (refresh != "") {
            ApiCalls.shared.refresh(refresh, fingerprint, callback)
        }
        else {
            callback("No refresh", "", "")
        }
    }

    fun getFingerPrint() : String {
        return fingerprint
    }

    private fun setFingerPrint(context: Context) : String {
            val pm = context.packageManager;
            val packageName = context.packageName;
            val flags = PackageManager.GET_SIGNATURES;
            var packageInfo: PackageInfo? = null;
            try {
                packageInfo = pm.getPackageInfo(packageName, flags);
            } catch (e : PackageManager.NameNotFoundException) {
                e.printStackTrace();
            }
            val signatures = packageInfo?.signatures;
            val cert = signatures?.get(0)?.toByteArray();
            val input = ByteArrayInputStream(cert);
            var cf: CertificateFactory? = null;
            try {
                cf = CertificateFactory.getInstance("X509");
            } catch (e : CertificateException) {
                e.printStackTrace();
            }
            var c: Certificate? = null;
            try {
                if (cf != null) {
                    c = cf.generateCertificate(input) as X509Certificate
                };
            } catch (e : CertificateException) {
                e.printStackTrace();
            }
            var hexString : String? = null;
            try {
                val md = MessageDigest.getInstance("SHA1");
                val publicKey = c?.encoded?.let { md.digest(it) };
                hexString = byte2HexFormatted(publicKey);
            } catch (e1 : NoSuchAlgorithmException) {
                e1.printStackTrace();
            } catch (e : CertificateEncodingException) {
                e.printStackTrace();
            }
            return hexString!!;
    }

    private fun byte2HexFormatted(arr: ByteArray?) : String {
        val str = StringBuilder((arr?.size ?: 1) * 2);
        if (arr != null) {
            for (i in arr.indices) {
                var h = Integer.toHexString(arr[i].toInt());
                val l = h.length;
                if (l == 1) h = "0$h";
                if (l > 2) h = h.substring(l - 2, l);
                str.append(h.uppercase(Locale.ROOT));
                if (i < (arr.size - 1)) str.append(':');
            }
        }
        return str.toString();
    }

}