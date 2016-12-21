package exceptionloop

class ErrorsController {

    def index() { }

    def err500() {
	render(view: 'error')
    }

    def err404() {
        render(view: 'notFound')
    }
}
