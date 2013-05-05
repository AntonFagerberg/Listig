package controllers

import play.api._
import play.api.mvc._
import play.api.db.slick.DB

object Application extends Controller {
  
  def index = Action {
//    println(models.Users.create("anton@antonfage2rberg.com2", "test".toCharArray))
//    println(models.Items.updateRating(1, 2))
//    println(models.Users.authenticate("anton@antonfagerberg.com", "test".toCharArray))
    Ok(views.html.index("Your new application is ready."))
  }
  
}