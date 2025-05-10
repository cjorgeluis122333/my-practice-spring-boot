const Keys = {
    admin: "ADMIN", user: "USER", email: "userInfo_email", picture: "userInfo_picture"
}


document.addEventListener("DOMContentLoaded", () => {
    const cookies = getCookie("token")
    const profileImg = document.getElementById("imgProfile")
    const fetchUserInfo = async () => {
        try {

            const request = await fetch("http://localhost:8081/api/userInfo", {
                headers: {
                    'Content-Type': 'application/json', 'Authorization': 'Bearer ' + cookies
                }
            })

            if (!request.ok) {
                throw new Error("Invalid token")
            }

            const data = await request.json()
            const getRoll = data.roleEntities


            //Save roll
            for (let i = 0; i < getRoll.length; i++) {
                setCookie(getRoll[i].name, getRoll[i].name)
                console.log("Roll: " + getRoll[i].name)
            }
            setCookie(Keys.email, data.email)
            setCookie(Keys.picture, data.picture)
            console.log(data)
            //Test
            return data
        } catch (error) {
            console.error("Failed the user info")
        }


    }

    fetchUserInfo().then(value => {
        fetchPicture(value.picture).catch()
    })
    const fetchPicture = async (picture) => {
        try {

            const request = await fetch("http://localhost:8081/api/profile/picture/view/" + picture, {
                headers: {
                    'Content-Type': 'application/json', 'Authorization': 'Bearer ' + cookies
                }
            })
            if (!request.ok) {
                throw new Error("Image not fount")
            }

            const blob = await request.blob() //Converter the response in a BLOB
            const imageUrl = URL.createObjectURL(blob)//Create a local url

            profileImg.src = imageUrl
            return imageUrl
        } catch (error) {
            console.error(error)
        }

    }


    const showImg = (url) => {
        const imgElement = document.createElement("img")
        imgElement.src = url
        document.body.appendChild(imgElement)
    }


})

const btnLogOut = document.getElementById("btnLogOut")
btnLogOut.addEventListener("click", () => {

    clearClaims()


})


const clearClaims = () => {
    setCookie(Keys.admin, "")
    setCookie(Keys.user, "")
    setCookie(Keys.email, "")
}
