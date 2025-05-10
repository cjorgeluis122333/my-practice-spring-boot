document.addEventListener("DOMContentLoaded", () => {
   //-------------------------------Navigation (GetArgs)-------------------------
    //Get the string go after (.html?)
    const queryParams = window.location.search
    const parseParams = new URLSearchParams(queryParams)   //Parse the params
    //Get the values of the html query
    const userName = decodeURIComponent(parseParams.get("username"))
    const password = decodeURIComponent(parseParams.get("password"))
//-------------------------------------------------------------------------------

    const getUserName = document.getElementById("iUserName")
    const getPassword = document.getElementById("iPassword")


    if (userName !== "null" && password !== "null") {
        getUserName.setAttribute("value", userName)
        getPassword.setAttribute("value", password)
    }
    console.log(userName, password)


    const alert = document.getElementById("errorAlert")

    const btnCloseAlert = document.getElementById("btnCloseAlert")
    btnCloseAlert.addEventListener("click", () => {
        alert.setAttribute("hidden", "hidden")
    })


    const getButtonSingIn = document.getElementById("iSingIn")
    getButtonSingIn.addEventListener("click", () => {
        onSingIn().then()
    })


//This kind of function execute on start project
// const onLog = fetch("http://localhost:8081/login", {
//     method: "POST", headers: {
//         'Content-Type': 'application/json',
//     }, body: JSON.stringify({
//         username: "", password: ""
//     })
// }).then(response => {
//     if (!response.ok) {
//         throw new Error("Error")
//     }
//     return response.json()
// }).then(date => {
//     console.log(date)
// }).catch(error => {
//     console.error(error)
// })

    const onSingIn = async () => {
        try {

            const request = await fetch("http://localhost:8081/login", {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        username: getUserName.value,
                        password: getPassword.value
                    })
                }
            )
            if (!request.ok) {
                throw new Error(`Error HTTP: ${request.status}`);
            }
            //Susses
            const data = await request.json()

            //Save cookies
            setCookie("token", data.token)

            //Navigate to home
            window.location.href = "../../templates/html/home.html"


        } catch (error) {
            alert.removeAttribute("hidden")
            alert.children.item(0).innerHTML = "Invalid credential, the email is to shot"

            console.error('Error en la petición:', error);
        }

    }
})

