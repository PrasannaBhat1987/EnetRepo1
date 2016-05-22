$(document).ready(function(){

        //Check to see if the window is top if not then display button
        $(window).scroll(function(){
          if ($(this).scrollTop() > 100) {
            $('.scrollToTop').fadeIn();
          } else {
            $('.scrollToTop').fadeOut();
          }
        });
        
          //Click event to scroll to top
          $('.scrollToTop').click(function(){
            $('html, body').animate({scrollTop : 0},800);
            return false;
          });
          
          function Redirect() {
              window.location="http://localhost:8083/Enet3/dashboard.html";
           }
          
		  $('#login-button').click(function(){
				var email = $('#email').val();
				var password = $('#password').val();
								
					$.ajax({
						url: '/Enet3/rest/user/login',
						dataType: 'text',
						type: 'post',
						contentType: 'application/json',						
						data: JSON.stringify({
							  "username": email,
							  "password": password
							}),
						success: function( data, textStatus, jQxhr ){
							var d = new Date();
						    d.setTime(d.getTime() + (1*24*60*60*1000));
						    var expires = "expires="+ d.toUTCString();
						    data = data.substring(data.indexOf(":")).trim();
						    document.cookie = "Auth=" + data + "; " + expires;
							Redirect();
						},
						error: function( jqXhr, textStatus, errorThrown ){
							alert(errorThrown);
						}
					});
			});
		  
        });