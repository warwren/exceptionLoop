package exceptionloop

class UploadController {

    def index() { }

    def save() {
        log.error("Upload succeeded. Upload a file larger than 128k to trigger the problem.")
        redirect(action: 'index')
    }
}
