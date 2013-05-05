package models

import scala.slick.driver.MySQLDriver.simple._
import play.api.db.slick.DB
import play.api.Play.current

case class Item (
  id: Long,
  email: String,
  title: String,
  image: Option[String],
  rating: Int,
  description: Option[String],
  listId: Long
)

object Items extends Table[Item]("item") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def email = column[String]("email")
  def title = column[String]("title")
  def image = column[Option[String]]("image")
  def rating = column[Int]("rating")
  def description = column[Option[String]]("description")
  def listId = column[Long]("list_id")
  def * = id ~ email ~ title ~ image ~ rating ~ description ~ listId <> (Item, Item.unapply _)

  def all(listId: Long) = {
    DB.withSession { implicit session =>
      Query(Items).filter(_.listId === listId).list()
    }
  }

  def updateRating(id: Long, rating: Int) = {
    DB.withSession { implicit session =>
      val ratingColumn = for {
        item <- Items
        if item.id === id
      } yield item.rating

      ratingColumn.update(rating)
    }
  }
}
