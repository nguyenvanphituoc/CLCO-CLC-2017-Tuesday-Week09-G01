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
    	List<webInfo> webpages=webInfoService.findAllWebPages();
    	model.addAttribute("webpages",webpages);
        return "jsp/example";
    }
    
    
    
    @RequestMapping(value = { "/callpaper" }, method = RequestMethod.GET)
    public String callpaper(Model model){
		return "jsp/Default789b.jsp?ArticleId=fc8c1223-a9d2-46b5-9db3-665c778f0ac9";
    }
    
    @RequestMapping(value = { "/submission" }, method = RequestMethod.GET)
    public String submission(Model model){
		return "jsp/Defaultd478.jsp?ArticleId=945864b5-165f-45a3-8e75-a9ba5ac1f9ef";
    	
    	
    }
    
    @RequestMapping(value = { "/registation" }, method = RequestMethod.GET)
    public String registation(Model model){
		return "jsp/Defaulta8ef.jsp?ArticleId=707e0e24-5220-4821-a40f-4041eb76cc1a";	
    }
    
    @RequestMapping(value = { "/keynotespeaker" }, method = RequestMethod.GET)
    public String keynotespeaker(Model model){
		return "jsp/Default6b73.jsp?ArticleId=be204435-0615-4fc3-aa86-392d871decc8";	
    }
    
    @RequestMapping(value = { "/contact" }, method = RequestMethod.GET)
    public String contact(Model model){
		return "jsp/Default84ba.jsp?ArticleId=b4031ebe-e802-4402-a49a-5769943872a5";	
    }
    
    @RequestMapping(value = { "/home_page" }, method = RequestMethod.GET)
    public String homepage(Model model){
		return "jsp/Default71a5.jsp?PageId=8cb7e0c5-10f3-438b-8881-a74c24b373e3";	
    }
    @RequestMapping(value = { "/venuehotel" }, method = RequestMethod.GET)
    public String hotelmapping(Model model){
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
