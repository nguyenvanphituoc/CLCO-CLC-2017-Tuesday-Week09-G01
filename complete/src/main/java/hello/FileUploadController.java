package hello;

import hello.search.FileSearchService;
import hello.storage.StorageFileNotFoundException;
import hello.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import hello.webInfo;
import hello.WebInfoService;

import java.awt.Dialog.ModalExclusionType;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Controller
public class FileUploadController {

    private final StorageService storageService;
    private final FileSearchService fileSearchService;
    
    @Autowired
    WebInfoService webInfoService;
  

 
    @Autowired
    public FileUploadController(StorageService storageService, FileSearchService fileSearchService) {
        this.storageService = storageService;
        this.fileSearchService = fileSearchService;
    }
    
    @RequestMapping("/")
    public String listUploadedFiles(Model model) throws IOException
    {
    	webInfo home=webInfoService.findById(19);
    	model.addAttribute("home",home);
        return "jsp/Default71a5";
    }
    @RequestMapping("/admin")
    public String admin(Model model) throws IOException
    {
    	List<webInfo> webpages=webInfoService.findAllWebPages();
    	model.addAttribute("webpages",webpages);
        return "jsp/example";
    }
    
    
    
    @RequestMapping(value = { "/callpaper" }, method = RequestMethod.GET)
    public String callpaper(Model model){
    
    	webInfo callpaper=webInfoService.findById(10);
    	model.addAttribute("callpaper",callpaper);
		return "jsp/Default789b";
    }
    
    @RequestMapping(value = { "/submission" }, method = RequestMethod.GET)
    public String submission(Model model){
    	webInfo submission=webInfoService.findById(15);
    	model.addAttribute("submission",submission);
		return "jsp/Defaultd478";
    	
    	
    }
    
    @RequestMapping(value = { "/registation" }, method = RequestMethod.GET)
    public String registation(Model model)
    {
    	
    	webInfo registration=webInfoService.findById(16);
    	model.addAttribute("registration",registration);
		return "jsp/Defaulta8ef";	
    }
    
    @RequestMapping(value = { "/keynotespeaker" }, method = RequestMethod.GET)
    public String keynotespeaker(Model model){
    	webInfo keynotespeaker=webInfoService.findById(17);
    	model.addAttribute("keynotespeaker",keynotespeaker);
		return "jsp/Default6b73";	
    }
    
    @RequestMapping(value = { "/contact" }, method = RequestMethod.GET)
    public String contact(Model model){
    	webInfo contact=webInfoService.findById(20);
    	model.addAttribute("contact",contact);
		return "jsp/Default84ba";	
    }
    
    @RequestMapping(value = { "/home_page" }, method = RequestMethod.GET)
    public String homepage(Model model){
    	webInfo home=webInfoService.findById(19);
    	model.addAttribute("home",home);
		return "jsp/Default71a5";	
    }
    @RequestMapping(value = { "/venuehotel" }, method = RequestMethod.GET)
    public String hotelmapping(Model model){
    	webInfo venuehotel=webInfoService.findById(18);
    	model.addAttribute("venuehotel",venuehotel);
		return "jsp/Venue_Hotel";	
    }
    
    @RequestMapping(value = { "/latestnewsFirst" }, method = RequestMethod.GET)
    public String latestnewsFirst(Model model){
		return "jsp/index52be.jsp?ArticleId=3af2fa01-2645-4516-a65a-67bfec768e13";	
    }
    
    
    @RequestMapping(value = { "/latestnewsSecond" }, method = RequestMethod.GET)
    public String latestnewsSecond(Model model){
		return "jsp/indexe41c.jsp?ArticleId=bc474489-26ce-4997-a1c0-b37dce27b202";	
    }
    
    
    @RequestMapping(value = { "/latestnewsThird" }, method = RequestMethod.GET)
    public String latestnewsThird(Model model){
		return "jsp/indexc489.jsp?ArticleId=f796f938-0681-4d25-b47e-6bcd24536152";	
    }
    
    
	@RequestMapping(value = { "/delete-page-{pageID}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String pageID) {
		int id=Integer.parseInt(pageID);
		webInfoService.deleteUserById(id);
		
		return "redirect:/";
	}
	
	@RequestMapping(value = { "/new-webpage" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		webInfo webInfo = new webInfo();
		model.addAttribute("webInfo", webInfo);
		return "jsp/regist";
	}

	
	@RequestMapping(value = { "/new-webpage" }, method = RequestMethod.POST)
	public String saveUser(@Valid webInfo webInfo, BindingResult result,
			ModelMap model) 
	{
		webInfoService.saveWebpage(webInfo);
		return "redirect:/";
	}
    
    
	@RequestMapping(value = { "/edit-webpage-{pageID}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String pageID, ModelMap model) {
		int id=Integer.parseInt(pageID);
		webInfo webInfo = webInfoService.findById(id);
		model.addAttribute("webInfo", webInfo);
	
		return "jsp/regist";
	}
	
	@RequestMapping(value = { "/edit-webpage-{pageID}" }, method = RequestMethod.POST)
	public String updateUser(@Valid webInfo webInfo, 
			ModelMap model, @PathVariable int pageID) {

	

		webInfoService.updateWebpage(webInfo);

		
		return "redirect:/";
	}
    
    
    
    
    

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
    
    @GetMapping("/search")
    public String searchFiles(@RequestParam("fileName") String name, Model model) throws IOException {
    	model.addAttribute("result",fileSearchService.searchFile(name));
        return "uploadForm";
    }
    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
