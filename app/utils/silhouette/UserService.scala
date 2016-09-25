package utils.silhouette

import javax.inject.Inject

import Implicits._
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import daos.UserDao
import models.DBTableDefinitions._

import scala.concurrent.Future

class UserService @Inject() (userDao: UserDao) extends IdentityService[User] {
  def retrieve(loginInfo: LoginInfo): Future[Option[User]] = userDao.findByEmail(loginInfo)
  def save(user: User): Future[User] = userDao.save(user)
}