setRandomNumber = () => $("#randomNumber").load("/random");

setRandomString = () => {
    let length = document.getElementById("randomStringLength").value;
    let url = `randomString/${length}`;
    fetch(url).then(response => {
        return response.text();
    }).then(responseText => {
        document.getElementById("randomString").innerHTML = responseText;
    }).catch(error => {
        console.log(error)
    })
}

$("#randomNumberParagraph").on("click", setRandomNumber);
$("#randomStringParagraph").on("click", setRandomString);

setRandomNumber();
setRandomString();