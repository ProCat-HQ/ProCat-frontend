package com.example.procatfirst.shared_preferences

import android.content.Context
import android.content.SharedPreferences


class SessionManager(
    var context: Context
) {
    // Shared Preferences
    var sharedPrefer: SharedPreferences

    // Editor for Shared preferences
    var editor: SharedPreferences.Editor

    // Shared Pref mode
    var PRIVATE_MODE = 0

    // Constructor
    init {
        sharedPrefer = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPrefer.edit()
    }

    /**
     * Call this method on/after login to store the details in session
     */
    fun createLoginSession(userId: String?, catId: String?, catTyp: String?, batchId: String?) {

        // Storing userId in pref
        editor.putString(KEY_USERID, userId)

        // Storing catId in pref
        editor.putString(KEY_CATID, catId)

        // Storing catType in pref
        editor.putString(KEY_CATTYPE, catTyp)

        // Storing catType in pref
        editor.putString(KEY_BATCHID, batchId)

        // commit changes
        editor.commit()
    }

    val userDetails: HashMap<String, String?>
        /**
         * Call this method anywhere in the project to Get the stored session data
         */
        get() {
            val user = HashMap<String, String?>()
            user["userId"] = sharedPrefer.getString(KEY_USERID, null)
            user["batchId"] = sharedPrefer.getString(KEY_BATCHID, null)
            user["catId"] = sharedPrefer.getString(KEY_CATID, null)
            user["catType"] = sharedPrefer.getString(KEY_CATTYPE, null)
            return user
        }

    companion object {
        // Shared Pref file name
        private const val PREF_NAME = "MySession"

        // SHARED PREF KEYS FOR ALL DATA
        // User's UserId
        const val KEY_USERID = "userId"

        // User's categoryId
        const val KEY_CATID = "catId"

        // User's categoryType[Teacher, Student, etc.,]
        const val KEY_CATTYPE = "categoryType"

        // User's batchId[like class or level or batch]
        const val KEY_BATCHID = "batchId"
    }
}