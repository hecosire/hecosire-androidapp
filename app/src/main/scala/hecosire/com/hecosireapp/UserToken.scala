package hecosire.com.hecosireapp

import android.content.{Context, Intent}

import scala.beans.BeanProperty

object UserToken {

  val MISSING_VALUE: String = "MISSING"

  def getUserToken(context: Context): UserToken = {
    val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    val email = sharedPref.getString(context.getString(R.string.emailPreferenceKey), MISSING_VALUE)
    val token = sharedPref.getString(context.getString(R.string.tokenPreferenceKey), MISSING_VALUE)
    if (MISSING_VALUE == email || MISSING_VALUE == token) {
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