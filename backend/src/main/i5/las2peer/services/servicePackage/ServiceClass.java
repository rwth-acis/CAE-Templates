package i5.las2peer.services.$Lower_Resource_Name$;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.core.JsonProcessingException;

import i5.las2peer.api.Service;
import i5.las2peer.restMapper.HttpResponse;
import i5.las2peer.restMapper.MediaType;
import i5.las2peer.restMapper.RESTMapper;
import i5.las2peer.restMapper.annotations.Version;
import i5.las2peer.restMapper.tools.ValidationResult;
import i5.las2peer.restMapper.tools.XMLCheck;
import i5.las2peer.security.Context;
import i5.las2peer.security.UserAgent;
import i5.las2peer.services.servicePackage.database.DatabaseManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.Reader;
import io.swagger.models.Swagger;
import io.swagger.util.Json;
import net.minidev.json.JSONObject;

/**
 * 
 * $Service_Name$
 * 
 * This is a template for a very basic LAS2peer service that uses the LAS2peer Web-Connector for
 * RESTful access to it.
 * 
 */
@Path("/example")
@Version("$Microservice_Version$") // this annotation is used by the XML mapper
@Api
@SwaggerDefinition(
    info = @Info(title = "LAS2peer Template Service", version = "0.1",
        description = "A LAS2peer Template Service for demonstration purposes.",
        termsOfService = "http://your-terms-of-service-url.com",
        contact = @Contact(name = "John Doe", url = "provider.com",
            email = "john.doe@provider.com") ,
        license = @License(name = "your software license name",
            url = "http://your-software-license-url.com") ) )
public class TemplateService extends Service {

  /*
   * Database configuration
   */
  private String jdbcDriverClassName;
  private String jdbcLogin;
  private String jdbcPass;
  private String jdbcUrl;
  private String jdbcSchema;
  private DatabaseManager dbm;

  public TemplateService() {
    // read and set properties values
    // IF THE SERVICE CLASS NAME IS CHANGED, THE PROPERTIES FILE NAME NEED TO BE CHANGED TOO!
    setFieldValues();
    // instantiate a database manager to handle database connection pooling and credentials
    dbm = new DatabaseManager(jdbcDriverClassName, jdbcLogin, jdbcPass, jdbcUrl, jdbcSchema);
  }

  // //////////////////////////////////////////////////////////////////////////////////////
  // Service methods.
  // //////////////////////////////////////////////////////////////////////////////////////

  $Service_Methods$

  // //////////////////////////////////////////////////////////////////////////////////////
  // Methods required by the LAS2peer framework.
  // //////////////////////////////////////////////////////////////////////////////////////



  /**
   * This method is needed for every RESTful application in LAS2peer. There is no need to change!
   * 
   * @return the mapping
   */
  public String getRESTMapping() {
    String result = "";
    try {
      result = RESTMapper.getMethodsAsXML(this.getClass());
    } catch (Exception e) {

      e.printStackTrace();
    }
    return result;
  }

  // //////////////////////////////////////////////////////////////////////////////////////
  // Methods providing a Swagger documentation of the service API.
  // //////////////////////////////////////////////////////////////////////////////////////

  /**
   * Returns the API documentation of all annotated resources for purposes of Swagger documentation.
   * 
   * Note: If you do not intend to use Swagger for the documentation of your service API, this
   * method may be removed.
   * 
   * @return The resource's documentation.
   */
  @GET
  @Path("/swagger.json")
  @Produces(MediaType.APPLICATION_JSON)
  public HttpResponse getSwaggerJSON() {
    Swagger swagger = new Reader(new Swagger()).read(this.getClass());
    if (swagger == null) {
      return new HttpResponse("Swagger API declaration not available!",
          HttpURLConnection.HTTP_NOT_FOUND);
    }
    try {
      return new HttpResponse(Json.mapper().writeValueAsString(swagger), HttpURLConnection.HTTP_OK);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return new HttpResponse(e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR);
    }
  }

}
