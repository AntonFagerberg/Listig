package models

import scala.slick.driver.MySQLDriver.simple._
import play.api.db.slick.DB
import play.api.Play.current
import javax.crypto.spec.PBEKeySpec
import javax.crypto.SecretKeyFactory
import java.security.SecureRandom

/** User representation.
  *
  * This class should only be used internally for authentication and never
  * be passed to the view or controller.
  *
  * @param email    E-mail of user.
  * @param password Password of user (hashed).
  * @param salt     Salt used in password hash.
  */
case class User (
  email: String,
  password: Array[Byte],
  salt: Array[Byte]
)

object Users extends Table[User]("user") {
  def email = column[String]("email", O.PrimaryKey)
  def password = column[Array[Byte]]("password")
  def salt = column[Array[Byte]]("salt")
  def * = email ~ password ~ salt <> (User, User.unapply _)

  /** Create a new user account.
    *
    * @param email    E-mail address of user.
    * @param password Password of user (not hashed).
    * @return         Was the user created?
    */
  def create(email: String, password: Array[Char]): Boolean = {
    DB.withSession { implicit session =>
      val salt = randomSalt
      val hashedPassword = hash(password, salt)

      Users.insert(User(email, hashedPassword, salt)) == 1
    }
  }

  /** Authenticate user credentials against database.
    *
    * @param email    Email-address of user.
    * @param password Password of user (not hashed).
    * @return         Was the user authenticated?
    */
  def authenticate(email: String, password: Array[Char]): Boolean = {
    DB.withSession { implicit session =>
      val user = {
        for {
          user <- Users
          if user.email === email
        } yield user
      }.first

      user.password.sameElements(hash(password, user.salt))
    }
  }

  /** Returns byte array hash created with PBKDF2WithHmacSHA1.
    *
    * @param clearText  Text to be hashed.
    * @param salt       Salt used when hashing the clearText.
    * @return           Hashed clearText.
    */
  private def hash(clearText: Array[Char], salt: Array[Byte]): Array[Byte] = {
    // Read about these values for PBKDF2 before changing iterationCount or keyLength.
    // Beware of changing these values if there are existing users in the database (you might break them).
    val iterationCount = 20480
    val keyLength = 160

    val spec = new PBEKeySpec(clearText, salt, iterationCount, keyLength)
    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec).getEncoded
  }

  /** Generate a pseudo-random salt used when hashing passwords created with SHA1PRNG.
    *
    * The salt is considered a non-secret and can be stored with the passwords in the database.
    * When a password is changed, the salt can, but does not have to be modified.
    *
    * @return Pseudo-random generated salt.
    */
  private def randomSalt: Array[Byte] = {
    val numberOfBytes = 20
    val random = SecureRandom.getInstance("SHA1PRNG")
    val salt = new Array[Byte](numberOfBytes)
    random.nextBytes(salt)
    salt
  }
}