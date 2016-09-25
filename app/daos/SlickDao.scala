package daos

import models.DBTableDefinitions
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile

/**
 * CopyRright (c) 2016 IBM
 * Project:
 *
 * @Comments
 * @Author Zhong Han
 * @Created Date 2016/9/24
 */
trait SlickDao extends DBTableDefinitions with HasDatabaseConfigProvider[JdbcProfile]
