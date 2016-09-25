package utils.silhouette

import javax.inject.Inject

import models.DBTableDefinitions._
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import com.mohiva.play.silhouette.api.LoginInfo

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import Implicits._
import daos.UserDao

class PasswordInfoDAO @Inject() (val userDao: UserDao) extends DelegableAuthInfoDAO[PasswordInfo] {

  def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] =
    update(loginInfo, authInfo)

  def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = {
    userDao.findByEmail(loginInfo).map {
      case Some(user) if user.emailConfirmed == 1 => Some(user.password)
      case _ => None
    }

  }

  def remove(loginInfo: LoginInfo): Future[Unit] = userDao.remove(loginInfo)

  def save(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] =
    find(loginInfo).flatMap {
      case Some(_) => update(loginInfo, authInfo)
      case None => add(loginInfo, authInfo)
    }

  def update(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] =
    userDao.findByEmail(loginInfo).map {
      case Some(user) => {
        userDao.save(user.copy(password = authInfo))
        authInfo
      }
      case _ => throw new Exception("PasswordInfoDAO - update : the user must exists to update its password")
    }

}