package controllers

import play.api.mvc._

object User extends Controller {
  def signIn = Action {
    Ok(views.html.user.signIn())
  }
}
