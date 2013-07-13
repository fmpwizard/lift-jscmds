package bootstrap.liftweb

import net.liftmodules.FoBo
import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    // where to search snippet
    LiftRules.addToPackages("com.besterdesigns.lift")
    
   FoBo.InitParam.JQuery = FoBo.JQuery182
    FoBo.InitParam.ToolKit = FoBo.Bootstrap230
    FoBo.InitParam.ToolKit = FoBo.FontAwesome300

    FoBo.init()
    // Build SiteMap
    val aPage = Loc("A Page", "apage" :: Nil, "apage", Hidden);
    val bPage = Loc("B Page", "bpage" :: Nil, "apage", Hidden);
    val cPage = Loc("C Page", "cpage" :: Nil, "apage", Hidden);
    val cometPage = Loc("Comet Page", "comet" :: Nil, "comet", LocGroup("main"));

    def sitemap() = SiteMap(
      Menu("Home") / "index", // >> User.AddUserMenusAfter, // Simple menu form Menu with special Link
      Menu(aPage),
      Menu(bPage),
      Menu(cPage),
      Menu(cometPage)
      )
      
    LiftRules.setSiteMap(sitemap());
    
    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))
  }
}
