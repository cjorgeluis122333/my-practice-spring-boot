const cookies = getCookie("token")

window.addEventListener("click",()=>{
    fetchUserInfo().then()
})

const fetchUserInfo = async () => {

    try {

        const request = await fetch("http://localhost:8081/api/userInfo", {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + cookies
            }
        })

        if (!request.ok) {
            throw new Error("Invalid token")
        }

        const data = await request.json()
        document.write(data)
        console.log(data)
    } catch (error) {

    }

}

const getAllUsers = async () => {


}