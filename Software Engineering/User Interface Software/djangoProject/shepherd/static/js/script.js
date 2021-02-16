$(document).ready(function domTraversal() {
    $(".successMessage").hide();
    $(".button").on("click", function () {
        $(".successMessage").find("h5").addClass("selected");
        $(".successMessage").show();
    })
});

// Resize Window
// $(window).on("resize", function () {
//     $('.video-background-holder').css('height', $(this).height());
// }).trigger("resize");

//Like Button w/ Ajax
// $(document).ready(function () {
//     $('button.upvote').click(function () {
//         // console.log("Like clicked")
//         let story_id = $(this).attr("data-story-id");
//         let ajax_url = $(this).attr("data-ajax-url");
//
//         // Using the core $.ajax() method
//         $.ajax({
//
//             // The URL for the request
//             url: ajax_url,
//
//             // The data to send (will be converted to a query string)
//             data: {
//                 story_id: story_id
//             },
//
//             // Whether this is a POST or GET request
//             type: "POST",
//
//             // The type of data we expect back
//             dataType: "json",
//             headers: {"X-CSRFToken": csrftoken},
//             context: this
//         })
//
//             // Code to run if the request succeeds (is done);
//             // The response is passed to the function
//             .done(function (json) {
//                 if (json.success === 'success') {
//                     // alert("request received successfully")
//                     let likeDiv = $(this).siblings('div.likes');
//
//                     let newLike = json.likes;
//                     console.log(newLike);
//                     $(likeDiv).text(newLike);
//
//                     let successMsg = $('<p class="upvote-success">Upvote Successful!</p>');
//                     $(this).parent().append(successMsg);
//                     $(successMsg).appendTo($(this).parent()).fadeOut('slow', function () {
//                         $(this).remove();
//                     });
//                 } else {
//                     alert("Error: " + json.error);
//                 }
//             })
//
//             // Code to run if the request fails; the raw request and
//             // status codes are passed to the function
//             .fail(function (xhr, status, errorThrown) {
//                 alert("Sorry there was a problem!");
//                 console.log("Error: " + errorThrown);
//
//             })
//
//         // Code to run regardless of success or failure;
//         // .always(function (xhr, status) {
//         //     alert("The request is complete!");
//         // });
//     });
// });
//
// function getCookie(name) {
//     let cookieValue = null;
//     if (document.cookie && document.cookie !== '') {
//         const cookies = document.cookie.split(';');
//         for (let i = 0; i < cookies.length; i++) {
//             const cookie = cookies[i].trim();
//             // Does this cookie string begin with the name we want?
//             if (cookie.substring(0, name.length + 1) === (name + '=')) {
//                 cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
//                 break;
//             }
//         }
//     }
//     return cookieValue;
// }
//
// const csrftoken = getCookie('csrftoken');

// Old......................................................................
//Dom Event Delegation User Interaction
//Click on admin login submit button and display an error message popup if info is entered but missing
// an @ or error messages if nothing is entered
$(document).ready(function () {
    eventDelegation();
    domTraversal();
    checkQueryString();
    addToCartButton();
    // getCookie();
    reset();

});

var slideIndex = 0;
carousel();

function carousel() {
    var i;
    var x = document.getElementsByClassName("mySlides");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    slideIndex++;
    if (slideIndex > x.length) {
        slideIndex = 1
    }
    x[slideIndex - 1].style.display = "block";
    setTimeout(carousel, 2000); // Change image every 2 seconds
}

function reset() {
    if (window.history.replaceState) {
        window.history.replaceState(null, null, window.location.href);
    }
}

//Click on admin login submit button and display an error message popup if info is entered but missing
// an @ or error messages if nothing is entered
function eventDelegation() {
    // noinspection JSJQueryEfficiency
    $('#first_form').submit(function (e) {
        e.preventDefault();
        let email = $('#email').val();
        let password = $('#password').val();
        $(".error").remove();
        if (email.length < 1) {
            $('#email').after('<p class="error">This field is required</p>');
        }
        if (password.length < 8) {
            $('#password').after('<p class="error">Password must be at least 8 characters long</p>');
        }
    })
}

//Click on newsletter submit button and display a success message and search for it using DOM
// traversal and turn it green
function domTraversal() {
    $("#successMessage").hide();
    $(".button").on("click", function () {
        $("#successMessage").find("p").addClass("selected");
        $("#successMessage").show();
    })
}

function checkQueryString() {
    let queryString = window.location.search; //get query string from url
    console.log(queryString);
    let urlParams = new URLSearchParams(queryString);
    if (urlParams.has('Search')) {
        let keyword = urlParams.get("Search");
        if (keyword === 'dog') {
            window.alert("You searched dog");
        } else {
            window.alert("You did not search dog");
        }
    }
}

//  <!--Source: https://www.w3schools.com/howto/howto_css_delete_modal.asp-->
// Get the modal
let modal = document.getElementById('id01');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

// function addToCartButton() {
//     $('div.cart_total button.upvote').click(function () {
//         console.log("Add to cart clicked")
//         let story_id = $(this).attr("data-story-id");
//         let ajax_url = $(this).attr("data-ajax-url");
//         $.ajax({
//             url: ajax_url,
//             data: {
//                 story_id: story_id
//             },
//             type: "POST",
//             dataType: "json",
//             headers: {"X-CSRFToken": csrftoken},
//             context: this
//         })
//             .done(function (json) {
//                 if (json.success === 'success') {
//                     // alert("request received successfully")
//                     let cartDiv = $(this).siblings('div.cart');
//                     let newScore = json.cart;
//                     console.log(newScore);
//                     $(cartDiv).text(newScore);
//                     let successMsg = $('<p class="upvote-success">Upvote Successful!</p>');
//                     $(this).parent().append(successMsg);
//                     $(successMsg).appendTo($(this).parent()).fadeOut('slow', function () {
//                         $(this).remove();
//                     });
//                 } else {
//                     alert("Error: " + json.error);
//                 }
//             })
//             .fail(function (xhr, status, errorThrown) {
//                 alert("Sorry there was a problem!");
//                 console.log("Error: " + errorThrown);
//
//             })
//         // .always(function (xhr, status) {
//         //     alert("The request is complete!");
//         // });
//     });
// }

// function getCookie(name) {
//     let cookieValue = null;
//     if (document.cookie && document.cookie !== '') {
//         const cookies = document.cookie.split(';');
//         for (let i = 0; i < cookies.length; i++) {
//             const cookie = cookies[i].trim();
//             // Does this cookie string begin with the name we want?
//             if (cookie.substring(0, name.length + 1) === (name + '=')) {
//                 cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
//                 break;
//             }
//         }
//     }
//     return cookieValue;
// }
//
// const csrftoken = getCookie('csrftoken');
//
// function getCookie(name) {
//     let cookieValue = null;
//     if (document.cookie && document.cookie !== '') {
//         const cookies = document.cookie.split(';');
//         for (let i = 0; i < cookies.length; i++) {
//             const cookie = cookies[i].trim();
//
//             if (cookie.substring(0, name.length + 1) === (name + '=')) ;
//             break
//         }
//     }
//     return cookieValue;
//
// }

// const csrftoken = getCookie('csrftoken');

