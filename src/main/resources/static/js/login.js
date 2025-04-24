const getUserName = document.getElementById("iUserName")
const getPassword = document.getElementById("iPassword")

const getButtonSingIn = document.getElementById("iSingIn")
const state = document.getElementById("state")
getButtonSingIn.addEventListener("click", () => {
    onSingIn().then()
})


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
            stateHandler("E", "User or password wrong")
            throw new Error(`Error HTTP: ${request.status}`);
        }
        //Susses
        const data = await request.json()
        stateHandler("S", "Susses")
        //Save cookies
        setCookie("token", data.token)

    } catch (error) {
        console.error('Error en la petición:', error);
    }

}

const stateHandler = (actualState, message) => {
    const value = document.createElement("strong")
    value.innerHTML = message
    state.appendChild(value)
    if (actualState === "S") {
        state.setAttribute("class", "alert alert-success")

        setTimeout(() => {
            //Navigate home
            window.location.href = "../../templates/html/home.html"
        }, 1500)

    } else if (actualState === "E") {
        state.setAttribute("class", "alert alert-danger")
    } else {
        setTimeout(() => {

        }, 1000)
    }
}




