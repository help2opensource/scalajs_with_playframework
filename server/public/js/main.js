setRandomNumber = () => $("#randomNumber").load("/random")

$("#randomNumberParagraph").on("click", setRandomNumber)

setRandomNumber()
