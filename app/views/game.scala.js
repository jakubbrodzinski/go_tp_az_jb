@(roomNumber: Int, size: Int, command: String)

//Array of objects that holds stone location/color/radius/opacity
var stones = [];

//Object that holds game state
var state = { myColor: "", currentColor: ""};

//Array of objects that holds backup of the boards state
var backupStones = [];



//Functions that draws a grid on the board
function drawGrid(context, boardSize, boardHeight, boardWidth, padding) {
    for(i = 0; i < boardSize; i++) {
        context.moveTo(35 * i + padding, 0 + padding);
        context.lineTo(35 * i + padding, boardHeight + padding);
    }
    for(i = 0; i < boardSize; i++) {
        context.moveTo(0 + padding, 35 * i + padding);
        context.lineTo(boardWidth + padding, 35 * i + padding);
    }
    context.stroke();
}
//Function that draws stones on the board
function drawStones(context, boardSize, padding) {
    for(i = 0; i < boardSize; i++) {
        for(j = 0; j < boardSize; j++) {
            context.beginPath();
            context.arc(35 * i + padding, 35 * j + padding, 15, 0, 2 * Math.PI, false);
            context.fillStyle ="green";
            context.globalAlpha = 0;
            context.fill();
            context.stroke();
            stones.push({
                colour: 'green',
                x: 35 * i + padding,
                y: 35 * j + padding,
                radius: 15,
                opacity: 0
            });
            context.globalAlpha = 1;
        }
    }
}

//Function that draws a single stone
function drawStone(context, x, y, colour, canMove) {
    for(i = 0; i < stones.length; i++) {
        if(canMove) {
            if (x >= stones[i].x - stones[i].radius && x <= stones[i].x + stones[i].radius && y >= stones[i].y - stones[i].radius && y <= stones[i].y + stones[i].radius) {
                alert(stones[i].colour);
                if(stones[i].colour.localeCompare("green") == 0) {
                    context.beginPath();
                    context.arc(stones[i].x, stones[i].y, stones[i].radius, 0, 2 * Math.PI, false);
                    context.fillStyle = colour;
                    context.fill();
                    context.stroke();
                    stones[i].colour = colour;
                    changeTurn(state.currentColor);
                }
                else {
                    alert(stones[i].colour);
                }
            }
        }
    }
}

//Function that removes a single stone
function removeStone(x, y) {
    for(i = 0; i < stones.length; i++) {
        if((stones[i].x == x) && (stones[i].y == y)) {
            stones[i].colour = "green";
        }
    }
}

//Function that redraws the canvas
function redraw(context, size, width, height, padding) {
    context.save();
    context.clearRect(0, 0, width + 2 * padding, height + 2 * padding);
    context.restore();

    drawGrid(context, size, height, width, padding);

    for(i = 0; i < stones.length; i++) {
        context.beginPath();
        context.arc(stones[i].x, stones[i].y, stones[i].radius, 0, 2 * Math.PI, false);
        if(stones[i].colour.localeCompare("green") == 0)
            context.globalAlpha = 0;
        context.fillStyle = stones[i].colour;
        context.fill();
        context.stroke();
        context.globalAlpha = 1;
    }
}

$(function() {
    //Getting canvas properties
    var size = @size;
    var cw = (size-1) * 35;
    var ch = (size-1) * 35;
    var padding = 20;

    //Preparing board layout
    var canvas = document.getElementById("myCanvas");
    $(canvas).attr({width: cw + 2 * padding, height: ch + 2 * padding});
    var ctx = canvas.getContext("2d");

    //Drawing grid
    drawGrid(ctx, size, ch, cw, padding);

    //Drawing stones
    drawStones(ctx, size, padding);

    //Getting canvas properties
    var canvasOffsetTop = canvas.offsetTop;
    var canvasOffsetLeft = canvas.offsetLeft;
    setPoints(0, 0);



    //Preparing websocket
    var ws = new WebSocket("@routes.Application.initializeConnection(roomNumber, size, command).webSocketURL(request)");

    //Adding function that handles WebSocket receiving messages
    ws.onmessage = function(evt) {
        var data = evt.data;
        alert(data);
    };

    canvas.addEventListener('click', function(event) {
        var x = event.pageX - canvasOffsetLeft;
        var y = event.pageY - canvasOffsetTop;
        //dzialajace
        //drawStone(ctx, x, y, "white", state.myColor.localeCompare(state.currentColor) == 0);
        /*drawStone(ctx, x, y, "white", 1);
        if(x >= 300 && y >=  300) {
            removeStone(20, 20);
            removeStone(55, 55);
            redraw(ctx, size, cw, ch, padding);
        }*/
        ws.send(x + " " + y);
    });


});

//Function that changes game state - current color playing and board opacity
function changeTurn(currentTurn) {
    if(currentTurn.localeCompare("black") == 0) {
        state.currentColor = "white";
    }
    else {
        state.currentColor = "black";
    }
    var canvas = document.getElementById("myCanvas");
    if(document.getElementById("myCanvas").style.opacity.localeCompare("0.5") == 0) {
        $(canvas).animate({opacity: 1}, 700);
    }
    else {
        $(canvas).animate({opacity: 0.5}, 700);
    }
}

//Function that sets white point and black points
function setPoints(whitePoints, blackPoints) {
    document.getElementById("blackpoints-para").innerHTML = blackPoints;
    document.getElementById("whitepoints-para").innerHTML = whitePoints;
}

//Function that parses stone location to server readable form
function parseLocationToServerReadable(stoneX, stoneY, size, padding) {
    var serverX = '';
    var serverY = '';
    switch((stoneX - padding)/35) {

        case 0:
            serverX = "A";
            break;
        case 1:
            serverX = "B";
            break;
        case 2:
            serverX = "C";
            break;
        case 3:
            serverX = "D";
            break;
        case 4:
            serverX = "E";
            break;
        case 5:
            serverX = "F";
            break;
        case 6:
            serverX = "G";
            break;
        case 7:
            serverX = "H";
            break;
        case 8:
            serverX = "J";
            break;
        case 9:
            serverX = "K";
            break;
        case 10:
            serverX = "L";
            break;
        case 11:
            serverX = "M";
            break;
        case 12:
            serverX = "N";
            break;
        case 13:
            serverX = "O";
            break;
        case 14:
            serverX = "P";
            break;
        case 15:
            serverX = "Q";
            break;
        case 16:
            serverX = "R";
            break;
        case 17:
            serverX = "S";
            break;
        case 18:
            serverX = "T";
            break;
        default:
            serverX = "ZLE";
            break;
    }
    switch((stoneY - padding)/35) {
        case 0:
            serverY = size;
            break;
        case 1:
            serverY = size - 1;
            break;
        case 2:
            serverY = size - 2;
            break;
        case 3:
            serverY = size - 3;
            break;
        case 4:
            serverY = size - 4;
            break;
        case 5:
            serverY = size - 5;
            break;
        case 6:
            serverY = size - 6;
            break;
        case 7:
            serverY = size - 7;
            break;
        case 8:
            serverY = size - 8;
            break;
        case 9:
            serverY = size - 9;
            break;
        case 10:
            serverY = size - 10;
            break;
        case 11:
            serverY = size - 11;
            break;
        case 12:
            serverY = size - 12;
            break;
        case 13:
            serverY = size - 13;
            break;
        case 14:
            serverY = size - 14;
            break;
        case 15:
            serverY = size - 15;
            break;
        case 16:
            serverY = size - 16;
            break;
        case 17:
            serverY = size - 17;
            break;
        case 18:
            serverY = size - 18;
            break;
        default:
            serverY = -3;
            break;
    }
    var location = {positionX: serverX, positionY: serverY};
    return location;
}

//Function that parses stone position received from the server to client readable form
function parseLocationToClientReadable(stoneX, stoneY, size, padding) {
    var clientX = '';
    var clientY = '';

    switch(stoneX) {
        case "A":
            clientX = 0 *35 + padding;
            break;
        case "B":
            clientX = 1 * 35 + padding;
            break;
        case "C":
            clientX = 2 * 35 + padding;
            break;
        case "D":
            clientX = 3 * 35 + padding;
            break;
        case "E":
            clientX = 4 * 35 + padding;
            break;
        case "F":
            clientX = 5 * 35 + padding;
            break;
        case "G":
            clientX = 6 * 35 + padding;
            break;
        case "H":
            clientX = 7 * 35 + padding;
            break;
        case "J":
            clientX = 8 * 35 + padding;
            break;
        case "K":
            clientX = 9 * 35 + padding;
            break;
        case "L":
            clientX = 10 * 35 + padding;
            break;
        case "M":
            clientX = 11 * 35 + padding;
            break;
        case "N":
            clientX = 12 * 35 + padding;
            break;
        case "O":
            clientX = 13 * 35 + padding;
            break;
        case "P":
            clientX = 14 * 35 + padding;
            break;
        case "Q":
            clientX = 15 * 35 + padding;
            break;
        case "R":
            clientX = 16 * 35 + padding;
            break;
        case "S":
            clientX = 17 * 35 + padding;
            break;
        case "T":
            clientX = 18 * 35 + padding;
            break;
        default:
            clientX = -1;
            break;
    }

    switch(stoneY) {
        case "1":
            clientY = (size - 1) * 35 + padding;
            break;
        case "2":
            clientY = (size - 2) * 35 + padding;
            break;
        case "3":
            clientY = (size - 3) * 35 + padding;
            break;
        case "4":
            clientY = (size - 4) * 35 + padding;
            break;
        case "5":
            clientY = (size - 5) * 35 + padding;
            break;
        case "6":
            clientY = (size - 6) * 35 + padding;
            break;
        case "7":
            clientY = (size - 7) * 35 + padding;
            break;
        case "8":
            clientY = (size - 8) * 35 + padding;
            break;
        case "9":
            clientY = (size - 9) * 35 + padding;
            break;
        case "10":
            clientY = (size - 10) * 35 + padding;
            break;
        case "11":
            clientY = (size - 11) * 35 + padding;
            break;
        case "12":
            clientY = (size - 12) * 35 + padding;
            break;
        case "13":
            clientY = (size - 13) * 35 + padding;
            break;
        case "14":
            clientY = (size - 14) * 35 + padding;
            break;
        case "15":
            clientY = (size - 15) * 35 + padding;
            break;
        case "16":
            clientY = (size - 16) * 35 + padding;
            break;
        case "17":
            clientY = (size - 17) * 35 + padding;
            break;
        case "18":
            clientY = (size - 18) * 35 + padding;
            break;
        case "19":
            clientY = (size - 19) * 35 + padding;
            break;
        default:
            clientY = -3;
            break;
    }
    var location = {positionX: clientX, positionY: clientY};
    return location;
}

