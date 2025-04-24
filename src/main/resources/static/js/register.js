const getUsername = document.getElementById("iUserName")
const getEmail = document.getElementById("iEmail")
const getPass = document.getElementById("iPassword")
const getPassRepeat = document.getElementById("iPasswordRepeat")

const btnRegister = document.getElementById("btn-register")

const stateError = document.getElementById("stateError")
const stateLoading = document.getElementById("stateLoading")

btnRegister.addEventListener("click", () => {
    onRegister().then(r => {

    })

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


const onRegister = async () => {
    const userName = getUsername.value
    const password = getPass.value
    const passRepeat = getPassRepeat.value
    const email = getEmail.value
    if (userName.length < 3 || password.length < 3 || password.length < 3 || passRepeat !== password || email.length < 3) {

        stateHandler("E")

    } else {
        await makePetition()
    }


}

const makePetition = async () => {


    try {
//                                                   http://localhost:8081/api/register
        const request = await fetch("http://localhost:8081/api/register", {
            method: 'POST', headers: {
                'Content-Type': 'application/json',
            }, body: JSON.stringify({
                username: userName, password: password, email: email
            })
        })
        if (!request.ok) {
            throw new Error(`Error HTTP: ${request.status}`);
        }
        //Susses
        const data = await request.json()
        console.log(data)


    } catch (error) {
        console.error('Error en la petición:', error);
    }
}

const onFeatchAll = async () => {

    try {

        const request = await fetch("http://localhost:8081/admin/users", {
            method: 'GET', headers: {
                'Content-Type': 'application/json',
            },
        })
        if (!request.ok) {
            throw new Error(`Error HTTP: ${request.status}`);
        }
        //Susses
        const data = await request.json()
        console.log(data)


    } catch (error) {
        console.error('Error en la petición:', error);
    }

}

const onLoading = () => {
    setInterval(stateHandler("L"), 20)


}


const stateHandler = (actualState) => {
    if (actualState === "E") {
        stateError.innerHTML = "Your register info is incomplete"
    }
    //else if (actualState === "S") {
    //     setTimeout(() => {
    //
    //     }, 1000)
    //     stateLoading.setAttribute("style", "width: 40%")
    //
    // } else if (actualState === "L") {
    //     setTimeout(() => {
    //
    //
    //     }, 2000)
    //     stateLoading.setAttribute("style", "width: 40%")
    //
    // }

}