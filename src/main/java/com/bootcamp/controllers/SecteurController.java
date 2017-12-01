package com.bootcamp.controllers;

//@RequestMapping("/secteur")
//@Api(value = "Secteur API", description = "Secteur API")
public class SecteurController {
//
//    @Autowired
//    SecteurService secteurService;
//
//    @Autowired
//    HttpServletRequest request;
//
//    @RequestMapping(method = RequestMethod.POST,value = "/")
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Create a new Secteur", notes = "Create a new Secteur")
//    public ResponseEntity<SecteurWs> create(@RequestBody @Valid Secteur Secteur) {
//
//        SecteurWs SecteurWs = new SecteurWs();
//        HttpStatus httpStatus = null;
//
//        try {
//            secteurService.create(Secteur);
//            SecteurWs.setData(Secteur);
//            httpStatus = HttpStatus.OK;
//        }catch (SQLException exception){
//            String errorMessage = exception.getMessage()==null?exception.getMessage():exception.getCause().getMessage();
//            Error error = new Error();
//            error.setMessage(errorMessage);
//            SecteurWs.setError(error);
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<SecteurWs>(SecteurWs, httpStatus);
//    }
//
//
//    @RequestMapping(method = RequestMethod.PUT, value = "/")
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Update a new Secteur", notes = "Update a new Secteur")
//    public ResponseEntity<SecteurWs> update(@RequestBody @Valid Secteur Secteur) {
//
//        SecteurWs SecteurWs = new SecteurWs();
//        HttpStatus httpStatus = null;
//
//        try {
//            secteurService.update(Secteur);
//            SecteurWs.setData(Secteur);
//            httpStatus = HttpStatus.OK;
//        }catch (SQLException exception){
//            String errorMessage = exception.getMessage()==null?exception.getMessage():exception.getCause().getMessage();
//            Error error = new Error();
//            error.setMessage(errorMessage);
//            SecteurWs.setError(error);
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<SecteurWs>(SecteurWs, httpStatus);
//    }
//
//    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Delete a Secteur", notes = "Delete a Secteur")
//    public ResponseEntity<SecteurWs> delete(@PathVariable(name = "id") int id) {
//
//        SecteurWs SecteurWs = new SecteurWs();
//        HttpStatus httpStatus = null;
//
//        try {
//            Secteur Secteur = secteurService.delete(id);
//            SecteurWs.setData(Secteur);
//            httpStatus = HttpStatus.OK;
//        }catch (SQLException exception){
//            String errorMessage = exception.getMessage()==null?exception.getMessage():exception.getCause().getMessage();
//            Error error = new Error();
//            error.setMessage(errorMessage);
//            SecteurWs.setError(error);
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<SecteurWs>(SecteurWs, httpStatus);
//    }
//
//
//    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Read a Secteur", notes = "Read a Secteur")
//    public ResponseEntity<SecteurWs> read(@PathVariable(name = "id") int id) {
//
//        SecteurWs SecteurWs = new SecteurWs();
//        HttpStatus httpStatus = null;
//
//        try {
//            Secteur Secteur = secteurService.read(id);
//            SecteurWs.setData(Secteur);
//            httpStatus = HttpStatus.OK;
//        }catch (SQLException exception){
//            String errorMessage = exception.getMessage()==null?exception.getMessage():exception.getCause().getMessage();
//            Error error = new Error();
//            error.setMessage(errorMessage);
//            SecteurWs.setError(error);
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<SecteurWs>(SecteurWs, httpStatus);
//    }
//
//    @RequestMapping(method = RequestMethod.GET)
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Read a Secteur", notes = "Read a Secteur")
//    public ResponseEntity<List<Secteur>> read() {
//        List<Secteur> secteurs=new ArrayList<Secteur>();
//        HttpStatus httpStatus = null;
//
//        try {
//             secteurs = secteurService.read(request);
//
//            httpStatus = HttpStatus.OK;
//        }catch (SQLException | IllegalAccessException | DatabaseException | InvocationTargetException exception){
//            String errorMessage = exception.getMessage()==null?exception.getMessage():exception.getCause().getMessage();
//
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<List<Secteur>>(secteurs, httpStatus);
//    }
}
