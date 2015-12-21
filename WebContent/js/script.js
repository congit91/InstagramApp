var NUMBER_PIC_PER_1_LOAD = 4;
var MAX_LENGTH = 20;
var accessToken = $('#access-token').val();
var numberLoad = 0, photoCount = 0;
var lengthOfAllPhotos;
var photoCount;

var url;
$(document).ready(function() {
	
	var userId = $('#user-id').val();
	if (userId != null && userId != undefined) {
		url = "https://api.instagram.com/v1/users/" + userId
				+ "/media/recent?access_token=" + accessToken;
		loadPhotos();
	}
});

$(window).scroll(function() {
    if(isScrolling() && hasPhotoNoLoad()) {
    	loadPhotos();
    }
});

function hasPhotoNoLoad() {
	return lengthOfAllPhotos != photoCount;
}

function isScrolling() {
	return $(window).scrollTop() == $(document).height() - $(window).height();
}

function loadPhotos() {
	$.ajax({
		type : "GET",
		dataType : "jsonp",
		cache : false,
		url : url,
		success : function(response) {
			numberLoad++;
			photoCount = NUMBER_PIC_PER_1_LOAD * numberLoad;
			lengthOfAllPhotos = response.data != 'undefined' ? response.data.length : 0;
			if (lengthOfAllPhotos > 0) {
				if (photoCount >= lengthOfAllPhotos) {
					NUMBER_PIC_PER_1_LOAD -= photoCount - lengthOfAllPhotos;
					photoCount = lengthOfAllPhotos;
					$('#btn-load').hide();
				} else {
					$('#btn-load').show();
				}
				var indexOfNextPhoto = photoCount - NUMBER_PIC_PER_1_LOAD;
				var i = indexOfNextPhoto > 0 ? indexOfNextPhoto : 0;
				for (i; i < photoCount; i++) {
					$('<img>', {
						src : response.data[i].images.standard_resolution.url
					}).appendTo($("#photos"));
				}
				$('#numberOfPhotos').text(photoCount);
				$('#photos > img').addClass('col-lg-3 col-md-4 col-sm-6 col-xs-12');
				$('#photos > img').attr('data-toggle', 'modal');
				$('#photos > img').attr('data-target', '#viewPhoto');
				$('#photos > img').click(function(){
					var src = $(this).attr('src');
					$('#img-src').attr('src', src);
				});
			}
		}
	});
}

function centerModal() {
    $(this).css('display', 'block');
    var $dialog = $(this).find(".modal-dialog");
    var offset = ($(window).height() - $dialog.height()) / 2;
    // Center modal vertically in window
    $dialog.css("margin-top", offset);
}

$('.modal').on('show.bs.modal', centerModal);
$(window).on("resize", function () {
    $('.modal:visible').each(centerModal);
});

function checkValidation() {
	var username = $('#user-name').val();
	if (username == null || username == undefined || username == '') {
		$('#user-name-info').show();
		$('#user-name-info').text('Please enter user name.');
		return false;
	}
	return true;
}