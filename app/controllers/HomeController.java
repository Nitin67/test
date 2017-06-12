package controllers;

import java.io.File;
import java.io.IOException;
import views.html.*;
import org.models.Doctor;

import com.google.inject.Inject;

import play.api.Play;
import play.libs.Scala;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * This controller contains an action to handle HTTP requests to the
 * application's home page.
 */
public class HomeController extends Controller {


	/**
	 * An action that renders an HTML page with a welcome message. The
	 * configuration in the <code>routes</code> file means that this method will
	 * be called when the application receives a <code>GET</code> request with a
	 * path of <code>/</code>.
	 */
	public Result index(String id) {
		System.out.println(id);
		return ok(index.render("test"));
	}
	public Result index2(String id,String name) {
		System.out.println(id);
		return ok(index.render("test"));
	}

	public Result test() {
		Doctor doc = new Doctor();
		doc.setAddress("address");
		doc.setAge(22);
		doc.setConsultantFee(312);
		doc.setName("Doctor");
		doc.setSpeciality("Eye");
		return ok(test.render(doc));
	}

	public String getUri(String any) {
		if (any.equals("patient_detail")) {
			return "/public/html/patient_detail.html";
		} else if (any.equals("doc_detail")) {
			return "/public/html/doc_detail.html";
		} else if (any.equals("pharmacist_detail")) {
			return "/public/html/pharmacist_detail.html";
		} else if (any.equals("patient_form")) {
			return "/public/html/patient_form.html";
		} else if (any.equals("doctor_form")) {
			return "/public/html/doctor_form.html";
		} else if (any.equals("pharmacist_form")) {
			return "/public/html/pharmacist_form.html";
		}
		return "error";
	}

	public Result loadPublicHTML(String any) throws IOException {
		File projectRoot = play.Play.application().path();
		System.out.println(projectRoot);
		File file = new File(projectRoot, getUri(any));
		System.out.println(file);
		if(file.exists())
			return ok(file);
//			return ok().as("text/html");
		else
			return notFound();
	}

}
