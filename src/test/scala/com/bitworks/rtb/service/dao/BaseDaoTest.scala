package com.bitworks.rtb.service.dao

import java.io.File

import com.typesafe.config.ConfigFactory
import org.dbunit.JdbcDatabaseTester
import org.dbunit.database.DatabaseConfig
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory
import org.dbunit.operation.DatabaseOperation
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import scaldi.Module
import scaldi.Injectable._

/**
  * Base class for DAO tests.
  *
  * @author Egor Ilchenko
  */
trait BaseDaoTest extends FlatSpec with BeforeAndAfterAll with Matchers {
  private val conf = ConfigFactory.load()
  private val host = conf.getString("db.dataSource.serverName")
  private val port = conf.getString("db.dataSource.portNumber")
  private val dbName = conf.getString("db.dataSource.databaseName")
  private val dcClassName = conf.getString("db.dataSourceClassName")
  private val user = conf.getString("db.dataSource.user")
  private val password = conf.getString("db.dataSource.password")
  private val connectionUrl = s"jdbc:postgresql://$host:$port/$dbName"

  private val tester = new JdbcDatabaseTester(
    dcClassName,
    connectionUrl,
    user,
    password)

  val connection = tester.getConnection
  
  val dbConfig = connection.getConfig
  dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory)

  implicit val dbModule = new Module {
    bind[DbContext] toNonLazy new DbContext("db")
    bind[CacheUpdater] toNonLazy new CacheUpdater
  }

  override def beforeAll(): Unit = {
    val path = getClass.getResource("setup.xml").getPath
    val dataSet = new FlatXmlDataSetBuilder().setColumnSensing(true).build(new File(path))
    DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet)
  }

  override def afterAll(): Unit = {
    connection.close()
    val context = inject[DbContext]
    context.close()
  }

  /**
    * Loads dataset from class path and execute it
    *
    * @param filename dataset filename
    * @param op       database operation
    */
  def executeQuery(filename: String, op: DbOperation) = {
    val path = getClass.getResource(filename).getPath
    val dataSet = new FlatXmlDataSetBuilder().setColumnSensing(true).build(new File(path))

    op match {
      case Update => DatabaseOperation.UPDATE.execute(connection, dataSet)
      case Insert => DatabaseOperation.INSERT.execute(connection, dataSet)
    }
  }
}

/** Database operation. */
trait DbOperation

/** Insert operation. */
case object Update extends DbOperation

/** Update operation. */
case object Insert extends DbOperation
