package controllers

import javax.inject.{ Inject, Singleton }

import com.mohiva.play.silhouette.api.Silhouette
import play.api._
import play.api.i18n.{ Lang, MessagesApi }
import utils.silhouette._

@Singleton
class Application @Inject() (val silhouette: Silhouette[MyEnv], val messagesApi: MessagesApi) extends AuthController {

  def index = UserAwareAction { implicit request =>
    Ok(views.html.index())
  }

  def myAccount = SecuredAction { implicit request =>
    Ok(views.html.myAccount())
  }

  // REQUIRED ROLES: serviceA (or master)
  def serviceA = SecuredAction(WithService("serviceA")) { implicit request =>
    Ok(views.html.serviceA())
  }

  // REQUIRED ROLES: serviceA OR serviceB (or master)
  def serviceAorServiceB = SecuredAction(WithService("serviceA", "serviceB")) { implicit request =>
    Ok(views.html.serviceAorServiceB())
  }

  // REQUIRED ROLES: serviceA AND serviceB (or master)
  def serviceAandServiceB = SecuredAction(WithServices("serviceA", "serviceB")) { implicit request =>
    Ok(views.html.serviceAandServiceB())
  }

  // REQUIRED ROLES: master
  def settings = SecuredAction(WithService("master")) { implicit request =>
    Ok(views.html.settings())
  }

  def selectLang(lang: String) = UnsecuredAction { implicit request =>
    Logger.logger.debug("Change user lang to : " + lang)
    request.headers.get(REFERER).map { referer =>
      Redirect(referer).withLang(Lang(lang))
    }.getOrElse {
      Redirect(routes.Application.index).withLang(Lang(lang))
    }
  }

}