package ws;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import entities.Voiture;
import model.VoitureDao;

@Path("voiture")
public class VoitureService {
VoitureDao vd = new VoitureDao();


@DELETE
@Path("/delet/{numEnrg}")
@Produces(MediaType.APPLICATION_JSON)
public Response delete( @PathParam("numEnrg") Integer numEnrg){ 
	vd.delete(vd.findbyCodeC(numEnrg));
return Response.ok().header("Access-Control-Allow-Origin", "*") 
			.header("Access-Control-Allow-Methods", "GET , POST , DELETE , PUT ,OPTIONS , HEAD")
			.build(); 
	
}


@GET
@Path("/findAll")
@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public List<Voiture> getAllProduct(){
	
	return vd.getAllProduct();
}


@GET
@Path("/find/{numEnrg}")
@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public Response findAllById(@PathParam("numEnrg") Integer numEnrg){
	Voiture v= new Voiture();
v=vd.findbyCodeC(numEnrg);
if(v == null) return null ;
	return Response.ok().entity(new GenericEntity<Voiture>(v){})
			.header("Access-Control-Allow-Origin", "*") 
			.header("Access-Control-Allow-Methods", "GET , POST , DELETE , PUT ,OPTIONS , HEAD")
			.build(); 
} 

@GET
@Path("/findDes/{descriptionVoiture}")
@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public List<Voiture> findAllByDescription(@PathParam("descriptionVoiture") String descriptionVoiture){
	return vd.findbyDescription(descriptionVoiture);
} 

@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/plus")
public Response add( Voiture vtr){ 
vd.save(vtr);
	return Response.ok().header("Access-Control-Allow-Origin", "*") 
			.header("Access-Control-Allow-Methods", "GET , POST , DELETE , PUT ,OPTIONS , HEAD")
			.build(); 
}


@PUT
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/edit")
public Response edit( Voiture vtrmod){ 
vd.update(vtrmod);
	return Response.ok().header("Access-Control-Allow-Origin", "*") 
			.header("Access-Control-Allow-Methods", "GET , POST , DELETE , PUT ,OPTIONS , HEAD")
			.build(); 
}

/**
 * public Response add( @FormParam("personName") String name , @FormParam("personPrenom") String prenom ,
		@FormParam("depot") String depot, @FormParam("descriptionVoiture") String description ,
		@FormParam("personCin") int cin, @FormParam("source") String source,@FormParam("datEnregitre") Date date){ 
	Voiture v = new Voiture(); v.setNumEnrg(3);
	v.setPersonCin(cin); v.setDatEnregitre(date);
	v.setDepot(depot); v.setDescriptionVoiture(description);
	v.setPersonName(name);
	v.setPersonPrenom(prenom);v.setSource(source);
vd.save(v);
	return Response.ok().header("Access-Control-Allow-Origin", "*") 
			.header("Access-Control-Allow-Methods", "GET , POST , DELETE , PUT ,OPTIONS , HEAD")
			.build(); 
}
@DELETE
@Path("/delete/{numEnrg}")
@Produces(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public Response delete(@PathParam("numEnrg") Integer numEnrg) throws URISyntaxException {
	vd.delete(vd.findbyCodeC(numEnrg));
	return Response.status(200).entity("user a code "+numEnrg+ "asupprimee").build();
}

**/
//@Produces("application/x-www-form-urlencoded")
//@Consumes("application/x-www-form-urlencoded")

}
