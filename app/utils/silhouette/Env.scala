package utils.silhouette

import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import models.DBTableDefinitions._

trait MyEnv extends Env {
  type I = User
  type A = CookieAuthenticator
}