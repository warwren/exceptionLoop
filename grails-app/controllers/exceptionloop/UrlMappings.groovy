package exceptionloop

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(controller: 'errors', action: 'err500')
        "404"(controller: 'errors', action: 'err404')
    }
}
