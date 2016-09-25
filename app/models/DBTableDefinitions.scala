package models

import slick.driver.JdbcProfile
import utils.silhouette.IdentitySilhouette

/**
 * CopyRright (c) 2016 IBM
 * Project:
 *
 * @Comments
 * @Author Zhong Han
 * @Created Date 2016/9/24
 */

object DBTableDefinitions extends {
  override protected val driver: JdbcProfile = slick.driver.MySQLDriver
} with DBTableDefinitions

trait DBTableDefinitions {

  protected val driver: JdbcProfile
  import driver.api._

  case class User(
      id: Long,
      email: String,
      emailConfirmed: Int,
      password: String,
      nick: String,
      firstName: String,
      lastName: String,
      service: String
  ) extends IdentitySilhouette {
    def key = email
    def fullName: String = firstName + " " + lastName
  }

  class Users(tag: Tag) extends Table[User](tag, "user") {
    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def email: Rep[String] = column[String]("email")
    def emailConfirmed: Rep[Int] = column[Int]("emailConfirmed")
    def password: Rep[String] = column[String]("password")
    def nick: Rep[String] = column[String]("nick")
    def firstName: Rep[String] = column[String]("firstName")
    def lastName: Rep[String] = column[String]("lastName")
    def service: Rep[String] = column[String]("service")

    def * = (id, email, emailConfirmed, password, nick, firstName, lastName, service) <> (User.tupled, User.unapply)
  }

  //Table queries
  val slickUsers = TableQuery[Users]
}
