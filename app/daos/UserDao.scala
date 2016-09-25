package daos

import javax.inject.Inject

import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import models.DBTableDefinitions._

import scala.concurrent.Future

/**
 * CopyRright (c) 2016 IBM
 * Project:
 *
 * @Comments
 * @Author Zhong Han
 * @Created Date 2016/9/24
 */
class UserDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  def findByEmail(email: String): Future[Option[User]] = {
    db.run(slickUsers.filter(_.email === email).result.headOption)
  }

  def save(user: User): Future[User] = db.run(slickUsers.insertOrUpdate(user)).map(_ => user)

  def remove(email: String): Future[Unit] =
    db.run(slickUsers.filter(_.email === email).delete).map(_ => ())
}
