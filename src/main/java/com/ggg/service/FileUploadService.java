package com.ggg.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.ggg.fus.domain.Application;
import com.ggg.fus.domain.ApplicationImpl;
import com.ggg.fus.domain.Profile;
import com.ggg.fus.domain.ProfileImpl;
import com.ggg.utils.DateUtils;

@Path("/fus")
public class FileUploadService {
	
	private final String FILE_REPOSITORY_PATH = "C:\\gilbert\\outfiles\\";
	
	@GET
	@Path("/profile/{userName}")
	@Produces("application/json")
	public Response getProfile(@Context HttpHeaders headers, @PathParam("userName") String msg) {
		
		// http://localhost:8080/FileUploadService/rest/fus/profile/gilbert
		
		//String result = "Restful example: " + msg;
		
		//Display the headers in sysout
		for (String header : headers.getRequestHeaders().keySet()) {
			System.out.println("Header: " + header + ":" + headers.getRequestHeader(header).get(0));
		}
		
		Profile profile = new ProfileImpl("gilbert", "abcde123");
		Application app1 = new ApplicationImpl("csidev", true, false);
		Application app2 = new ApplicationImpl("yoda", false, true);
		profile.addApplication(app1);
		profile.addApplication(app2);
		
		//return Response.status(200).entity(result).build();
		return Response.status(200).entity(profile).build();
	}
	
	@GET
	@Path("/file/{fileName}")
	@Produces("application/octet-stream")
	public File getFile(@PathParam("fileName") String fileName) {
		return new File(FILE_REPOSITORY_PATH + fileName);
	}
	
	/**
	@GET
	@Path("/files")
	@Produces("application/json")
	public Response getFiles() {
		
		
	}
	*/
	
	
	
	@POST
	@Path("/file")
	@Consumes("application/octet-stream") // mime type "text/plain" "image/png"
	public Response postFile(@QueryParam("targetFileName") String targetFileName, InputStream is) {
		
		DateUtils.setStart();
		int bytesRead = 0;
		try {

			OutputStream os = new FileOutputStream(new File(FILE_REPOSITORY_PATH + targetFileName ));
			bytesRead = IOUtils.copy(is, os);
			os.flush();
			os.close();
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	
		System.out.println("Copy duration in milliseconds: " + DateUtils.getDuration());
		
		//todo: set Location header
		
		//return Response.status(201).entity("Received file. Bytes read: " + bytesRead).build();
		return Response.created(URI.create("/fus/file/" + targetFileName)).build();
	}

	

	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public Response uploadFile(MultipartFormDataInput input) {
		
		String fileName = "";
		
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");
		
		for (InputPart inputPart : inputParts) {
			
			try {
				
				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);
				
				//convert the uploaded file into inputstream
				
				InputStream inputStream = inputPart.getBody(InputStream.class, null);
				
				byte[] bytes = IOUtils.toByteArray(inputStream);
				
				//constructs upload file path
				fileName = FILE_REPOSITORY_PATH + fileName;
				
				writeFile(bytes, fileName);
				
				System.out.println("Done!");
				
				
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return Response.status(200).entity("uploadFile called. Uploaded file name: " + fileName).build();
	}
	
	/**
	 * header sample
	 * {
	 * 		Content-type=[image/png],
	 * 		Content-Disposition=[form-data; name="file"; filename=filename.extension ]
	 * }
	 */
	private String getFileName(MultivaluedMap<String, String> header) {
		
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		
		for (String filename : contentDisposition) {
			
			System.out.println("file name: " + filename);
			if (filename.trim().startsWith("filename")) {
				
				String[] name = filename.split("=");
				String finalFileName = name[1].trim().replaceAll("\"", "");
				
				return finalFileName;
			}
		}
		return "unknown";
	}

	/**
	 * Save file
	 */
	private void writeFile(byte[] content, String fileName) throws IOException {
		
		File file = new File(fileName);
		
		if (!file.exists()) {
			file.createNewFile();
		}
		
		FileOutputStream fop = new FileOutputStream(file);
		
		fop.write(content);
		fop.flush();
		fop.close();
	}
}
