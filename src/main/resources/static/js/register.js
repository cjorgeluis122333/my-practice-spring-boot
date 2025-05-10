const getUsername = document.getElementById("iUserName")
const getEmail = document.getElementById("iEmail")
const getPass = document.getElementById("iPassword")
const getPassRepeat = document.getElementById("iPasswordRepeat")

const btnRegister = document.getElementById("btn-register")

const stateError = document.getElementById("stateError")
const stateLoading = document.getElementById("stateLoading")
const alert = document.getElementById("errorAlert")
const btnCloseAlert = document.getElementById("btnCloseAlert")

btnCloseAlert.addEventListener("click", () => {
    alert.setAttribute("hidden", "hidden")
})

btnRegister.addEventListener("click", () => {
    onRegister().then(r => {

    }).catch(() => {
        alert.removeAttribute("hidden")
        alert.children.item(0).innerHTML = "Invalid credentials"

    })

})


const onRegister = async () => {
    const userName = getUsername.value
    const password = getPass.value
    const passRepeat = getPassRepeat.value
    const email = getEmail.value
  const isValid = validateForm(userName, password, passRepeat, email)
    if (isValid === true) {
        console.log("true")
        await makePetition(userName, password, email)
            .then(() => {
                const encodeUserName = encodeURI(userName)
                const encodePassword = encodeURI(password)
                window.location.href = `../../templates/html/login.html?username=${encodeUserName}&password=${encodePassword}`
            }).catch(error => {
                alert.removeAttribute("hidden")
            })
    }else {
        console.log("False")
    }

}

const makePetition = async (userName, password, email) => {

    try {
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
        return await request.json()


    } catch (error) {
        console.error('Error en la petición:', error);
    }
}


const validateForm = (userName, password, passRepeat, email) => {

    if (userName.length <= 3) {
        alert.removeAttribute("hidden")
        alert.children.item(0).innerHTML = "Invalid credentials, username to short"
        return false
    } else if (password.length <= 3 || passRepeat !== password) {
        alert.removeAttribute("hidden")
        alert.children.item(0).innerHTML = "Invalid credentials, the password has to by bigger than 3 and equals to password repeat"
        return false
    } else if (email.length < 5) {
        alert.removeAttribute("hidden")
        alert.children.item(0).innerHTML = "Invalid credential, the email is to shot"
        return false
    }
    return true

}

