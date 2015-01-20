package hecosire.com.hecosireapp

import android.content.{Context, Intent}

import scala.beans.BeanProperty

object UserToken {

  def getUserToken(context: Context): UserToken = {
    val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    val email = sharedPref.getString(context.getString(R.string.emailPreferenceKey), "MISSING")
    val token = sharedPref.getString(context.getString(R.string.tokenPreferenceKey), "MISSING")
    if ("MISSING" == email || "MISSING" == token) {
      val goToNextActivity = new Intent(context.getApplicationContext, classOf[LoginActivity])
      context.startActivity(goToNextActivity)
      return null
    }
    new UserToken(email, token)
  }
}

class UserToken(@BeanProperty var email: String, @BeanProperty var token: String)
    {


}