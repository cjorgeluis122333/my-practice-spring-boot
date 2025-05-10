const generateErrorDialog = (containerId, message = "Invalid credentials", type = "success") => {

    const getContainer = document.getElementById(containerId)
    getContainer.setAttribute("class", `alert alert-${type} alert-dismissible  fade show`)
    getContainer.setAttribute("role", "alert")

    getContainer.innerHTML =
        `
   <a href="#" class="alert-link">an example link</a>. ${message}.
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
       
<button type="button" class="btn-close" data-dismiss="alert">&times;</button>
  <strong>Error!</strong> ${message}`


    /*
     <div class="alert alert-danger alert-dismissible fade show" role="alert">
          A simple danger alert with <a href="#" class="alert-link">an example link</a>. Give it a click if you like.
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>

     */
}