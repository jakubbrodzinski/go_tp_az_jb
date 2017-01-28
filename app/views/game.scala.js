@(roomNumber: Int, size: Int, command: String)

//Array of objects that holds stone location/color/radius/opacity
var stones = [];

//Object that holds game state
var state = { myColor: "", currentColor: ""};

//Object that holds canvas properties
var canvasProps = { size: 0, cw: 0, ch: 0, padding: 20, offsetTop: 0, offsetLeft: 0};

//Array of objects that holds backup of the boards state
var backupStones = [];

//Function that copies the current board into backup array of stones
function doBackup() {
    for(i = 0; i < stones.length; i++) {
        backupStones.push({
            colour: stones[i].colour,
            x: stones[i].x,
            y: stones[i].y,
            radius: stones[i].radius,
            opacity: stones[i].opacity
        })
    }
}

//Functions that recovers the board from the backup array
function restoreBoard(context, size, height, width, padding) {
    for(i = 0; i < backupStones.length; i++) {
        stones[i].x = backupStones[i].x;
        stones[i].y = backupStones[i].y;
        stones[i].radius = backupStones[i].radius;
        stones[i].colour = backupStones[i].colour;
        stones[i].opacity = backupStones[i].opacity;
    }
    redraw(context, size, width, height, padding);

}

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
            stones.push({
                colour: 'green',
                x: 35 * i + padding,
                y: 35 * j + padding,
                radius: 15,
                opacity: 0
            });
        }
    }
}

//Function that draws a single stone from user input
function drawStone(context, x, y, colour, canMove) {
    for(i = 0; i < stones.length; i++) {
        if(canMove) {
            if (x >= stones[i].x - stones[i].radius && x <= stones[i].x + stones[i].radius && y >= stones[i].y - stones[i].radius && y <= stones[i].y + stones[i].radius) {
                if(stones[i].colour.localeCompare("green") == 0) {
                    context.beginPath();
                    context.arc(stones[i].x, stones[i].y, stones[i].radius, 0, 2 * Math.PI, false);
                    context.fillStyle = colour;
                    context.fill();
                    context.stroke();
                    stones[i].colour = colour;
                    context.beginPath();
                }
                else {
                    alert(stones[i].colour);
                }
            }
        }
    }
}

//Function that finds stone coordinates from inserted location
function findStone(x, y) {
    for (i = 0; i < stones.length; i++) {
        if (x >= stones[i].x - stones[i].radius && x <= stones[i].x + stones[i].radius && y >= stones[i].y - stones[i].radius && y <= stones[i].y + stones[i].radius) {
            var location = parseLocationToServerReadable(stones[i].x, stones[i].y, canvasProps.size, canvasProps.padding);
            return location;
        }
    }
}

//Function that removes a single stone
//One have to redraw after using this method to apply changes to the board
function removeStone(x, y) {
    for(i = 0; i < stones.length; i++) {
        if((stones[i].x == x) && (stones[i].y == y)) {
            stones[i].colour = "green";
        }
    }
}

//Function that redraws the canvas
function redraw(context, size, width, height, padding) {
    context.clearRect(0, 0, width + 2 * padding, height + 2 * padding);
    drawGrid(context, size, height, width, padding);

    for(i = 0; i < stones.length; i++) {
        context.beginPath();
        context.arc(stones[i].x, stones[i].y, stones[i].radius, 0, 2 * Math.PI, false);
        if(stones[i].colour.localeCompare("green") == 0)
            context.globalAlpha = 0;
        context.fillStyle = stones[i].colour;
        context.fill();
        if(i != stones.length - 1)
            context.stroke();
        context.globalAlpha = 1;
        context.beginPath();
    }
}

$(function() {


    prepareCanvas(@size, 20);
    //Getting canvas properties

    setPoints("0", "0");

    //Preparing websocket
    var ws = new WebSocket("@routes.Application.initializeConnection(roomNumber, size, command).webSocketURL(request)");

    //Adding function that handles WebSocket receiving messages
    var firstMessage = 1;
    ws.onmessage = function(evt) {
        alert(evt.data);
        var data = evt.data;
        if(firstMessage == 1) {
            firstMessage = 0;
            var response = data.split("-");
            setPoints(response[1],response[2]);
            state.myColor = response[3];
            state.currentColor = "BLACK";
            canvasProps.size = response[4];
            document.getElementById("room-number").innerHTML = "Jestes w pokoju: " + response[5];
            if(state.myColor.localeCompare(state.currentColor) != 0) {
                var canvas = document.getElementById("myCanvas");
                $(canvas).animate({opacity: 0.5}, 700);
            }
        }
        else if(data.indexOf("CORRECT") != -1) {
            var res = data.split("-");
            setPoints(res[1],res[2]);
            var location = parseLocationToClientReadable(res[4], res[5], canvasProps.size, canvasProps.padding);
            drawStone(canvasProps.context, location.positionX, location.positionY, state.myColor, 1);
            for(i = 6; i < res.length; i += 2) {
                location = parseLocationToClientReadable(res[i], res[i+1], canvasProps.size, canvasProps.padding);
                removeStone(location.positionX, location.positionY);
            }
            redraw(canvasProps.context, canvasProps.size, canvasProps.width, canvasProps.height, canvasProps.padding);
            changeTurn();
        }
        else if(data.indexOf("CHANGE") != -1) {
            var res = data.split("-");
            setPoints(res[1],res[2]);
            var location = parseLocationToClientReadable(res[4], res[5], canvasProps.size, canvasProps.padding);
            drawStone(canvasProps.context, location.positionX, location.positionY, state.currentColor, 1);
            for(i = 6; i < res.length; i += 2) {
                location = parseLocationToClientReadable(res[i], res[i+1], canvasProps.size, canvasProps.padding);
                removeStone(location.positionX, location.positionY);
            }
            redraw(canvasProps.context, canvasProps.size, canvasProps.width, canvasProps.height, canvasProps.padding);
            changeTurn();
        }
        else if(data.startsWith("PASS")) {
            changeTurn();
        }
        else if(data.startsWith("CONCEDE")) {
            alert("Przeciwnik się poddał! Wygrana!");
        }
        else if(data.startsWith("QUIT")) {
            ws.close();
            alert("Przeciwnik wyszedl!");
        }
    };
    //Adding listener to canvas
    document.getElementById("myCanvas").addEventListener('click', function(event) {
        var x = event.pageX - canvasProps.offsetLeft;
        var y = event.pageY - canvasProps.offsetTop;
        //dzialajace
        //drawStone(ctx, x, y, "white", state.myColor.localeCompare(state.currentColor) == 0);
        drawStone(canvasProps.context, x, y, "white", 1);
        if(isMyTurn()) {
            var location = findStone(x, y);
            //ws.send("MOVE-"+ location.positionX + "-" + location.positionY);
            ws.send("POINTS-0-2-WHITE-9-1");
            drawStone(canvasProps.context, 20, 20, "black", 1);
            drawStone(canvasProps.context, 55, 55, "black", 1);
        }
    });

    //Adding listener to the passing button
    document.getElementById("pass-btn").onclick = function() {
        ws.send("PASS");
    };
    //Adding listener to the conceding button
    document.getElementById("concede-btn").onclick = function() {
        ws.send("CONCEDE");
    };

    $(window).on('beforeunload', function() {
        ws.send("QUIT");
    });

});

//Function that checks if it is current player turn
function isMyTurn() {
    return state.myColor.localeCompare(state.currentColor) == 0 ? 1 : 0;
}

//Function that changes game state - current color playing and board opacity
function changeTurn() {
    if(state.currentColor.localeCompare("BLACK") == 0) {
        state.currentColor = "WHITE";
    }
    else {
        state.currentColor = "BLACK";
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

//Function that prepares the canvas
function prepareCanvas(size, padding) {
    //Getting canvas properties
    canvasProps.size = size;
    canvasProps.width = (size-1) * 35;
    canvasProps.height = (size-1) * 35;
    canvasProps.padding = padding;

    //Preparing board layout
    var canvas = document.getElementById("myCanvas");


    $(canvas).attr({width: canvasProps.width + 2 * padding, height: canvasProps.height + 2 * padding});
    canvasProps.context = canvas.getContext("2d");


    //Drawing grid
    drawGrid(canvasProps.context, size, canvasProps.height, canvasProps.width, padding);

    //Drawing stones
    drawStones(canvasProps.context, size, padding);

    canvasProps.offsetTop = canvas.offsetTop;
    canvasProps.offsetLeft = canvas.offsetLeft;
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

