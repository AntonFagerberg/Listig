package models

import scala.slick.driver.MySQLDriver.simple._
import play.api.db.slick.DB
import play.api.Play.current

/** A users public profile.
  *
  * @param email  E-mail address of user.
  */
case class Profile (
  nickname: String,
  email: String
)

object Profiles extends Table[Profile]("user") {
  def nickname = column[String]("nickname", O.PrimaryKey)
  def email = column[String]("email")
  def * = nickname ~ email <> (Profile, Profile.unapply _)

  def find(email: String): Option[Profile] = {
    DB.withSession { implicit session =>
      Query(Profiles).filter(_.email === email).firstOption
    }
  }
}