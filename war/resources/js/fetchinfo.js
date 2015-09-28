$(function(){
	$("#submitData").click(function( event ) {
	    var reg = /.+/;
	    var input = $("#keyword").val();
	    $("#content_table").text("Searching result for '" + input + "'...");
	    if (reg.test(input)) {
	        var keywords = input.split(/\s+/);
	        var obj = {"keywords":[]};
            for (var i in keywords) {
                if (keywords[i]) {
                    obj["keywords"].push(keywords[i]);
                }
            }
            if (obj["keywords"].length == 0) {
                alert("Please input keywords");
                $("#keyword").val("");
            } else {
                var type = $("#type").val().toLowerCase();
                var requesturl = $BASE_URL + type;
                $.ajax({
                    type: "POST",
                    url: requesturl,
                    data: JSON.stringify(obj),
                    contentType:"application/json; charset=utf-8",
                    success: processData,
                    dataType: "json"
                });
            }
	    } else {
	        alert("Please input keywords");
	    }
	});
});

//var $BASE_URL = "http://localhost:8888/";
var $BASE_URL = "http://1-dot-allmusic-1057.appspot.com/";
function processData(data) {
    var result = document.createElement("table");
    result.setAttribute("border", "1");
    result.setAttribute("width", "70%");
    titles = {
        "artists":["Cover", "Artist", "Genre", "Year", "Details"],
        "albums":["Cover", "Title", "Artist", "Genre", "Year", "Details"],
        "songs":["Title", "Performer", "Composer", "Details"]
    };
    for (var key in data) {
        parseData(result, data[key], titles[key]);
    }
    $("#content_table").html(result);
}

function createFBCell(data, type) {
    var td = document.createElement("td");
    var FBcell = document.createElement("img");
    FBcell.src = "/resources/img/facebook_logo.jpg";
    FBcell.width = "100";
    td.appendChild(FBcell);
    FBcell.onclick = function() {FeedDialog(data, type)};
    return td;
}

function FeedDialog(data, type) {
    var obj = {
        method: 'feed',
    };
    switch (type) {
        case "artists": obj["name"] = data["artist"];
                        obj["description"] = "I like " + data["artist"] + " who is active since " + data["year"]+
                                    ". Genre of Music is: "+ data["genre"] +".";
                        if (!data["cover"])
                            obj["picture"] = $BASE_URL + "/resources/img/artists_cover.png";
                        else
                            obj["picture"] = data["cover"];
                        break;
        case "albums":  obj["name"] = data["title"];
                        obj["description"] = "I like " + data["title"]+" released in " + data["year"]+
                                    ". Artist: " + data["artist"] + ", Genre:" + data["genre"];
                        if (!data["cover"])
                            obj["picture"] = $BASE_URL + "/resources/img/albums_cover.png";
                        else
                            obj["picture"] = data["cover"];
                        break;
        case "songs":   obj["name"] = data["title"];
                        obj["description"] = "I like " + data["title"] + " composed by " + data["composer"] +
                                    ", Performer: " + data["performer"] + ".";
                        obj["picture"] = $BASE_URL + "/resources/img/songs_cover.png";
                        break;
    }
    obj["link"] = data["details"];
    obj["properties"] = {'Look at details':{'text':'here','href':data["details"]}};
    FB.ui(obj);
}

function createTextCell(data) {
    var td = document.createElement("td");
    td.appendChild(document.createTextNode(data));
    return td;
}

function createImgCell(url) {
    var td = document.createElement("td");
    var img = document.createElement("img");
    img.setAttribute("src", url);
    td.appendChild(img);
    return td;
}

function createAnchorCell(url, text) {
    var td = document.createElement("td");
    var anchor = document.createElement("a");
    anchor.setAttribute("href", url);
    anchor.appendChild(document.createTextNode(text));
    td.appendChild(anchor);
    return td;
}

function parseData(table, data, titles) {
    var type = $("#type").val().toLowerCase();
    titles.push("Post to FB");
    var tr = document.createElement("tr");
    for (var index in titles) {
        tr.appendChild(createTextCell(titles[index]));
    }
    table.appendChild(tr);
    for (var index in data) {
        tr = document.createElement("tr");
        for (var titleIndex in titles) {
            if (titles[titleIndex].toLowerCase() == "cover") {
                if (data[index]["cover"]) {
                    tr.appendChild(createImgCell(data[index]["cover"]));
                } else {
                    tr.appendChild(createTextCell("N.A."));
                }
            } else if (titles[titleIndex].toLowerCase() == "details") {
                if (data[index]["details"]) {
                    tr.appendChild(createAnchorCell(data[index]["details"], "details"));
                } else {
                    tr.appendChild(createTextCell("N.A."));
                }
            } else if (titles[titleIndex] == "Post to FB") {
                tr.appendChild(createFBCell(data[index], type));
            } else {
                tr.appendChild(createTextCell(data[index][titles[titleIndex].toLowerCase()]));
            }
        }
        table.appendChild(tr);
    }
    FB.XFBML.parse(table);
}