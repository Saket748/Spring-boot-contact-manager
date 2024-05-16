
 const toggleSidebar = ()=>{

     if($('.sidebar').is(":visible")){

         $('.sidebar').css("display", "none");
         $('.content').css("margin-left", "1%");
   //      $('.content').css("padding-left", "18%");
     }else{
         $('.sidebar').css("display", "block");
         $('.content').css("margin-left", "18%");
 //		$('.content').css("padding-left", "1%");
     }
 };

// js for search box -->
const search = () => {
// console.log("searching...");
let query = $("#search-input").val();
if (query == "") {
$(".search-result").hide();
} else {
//search
console.log(query);
let url = `http://localhost:8080/search/${query}`;
fetch(url).then((response) => {
return response.json();
})
.then((data) => {
    console.log(data);
    let text = `<div class='list-group'>`;

    data.forEach((contact) => {
        text +=`<a href='/user/${contact.cId}/contact' class='list-group-item list-group-item-action'> ${contact.name} </a>`
    });
    text += `</div>`;
    $(".search-result").html(text);
    $(".search-result").show();
});
}

};

//payment js start 

const paymentStart=()=>{
console.log("payment started...");
let amount = $("#payment_field").val();
console.log(amount);
if(amount=='' || amount==null){
//      alert("amount is required");
        swal("Empty !!","Amount required","error");
        return;
}
$.ajax(
    {
        url:'/user/create_order',
        data:JSON.stringify({amount:amount,info:"order_request"}),
        contentType:'application/json',
        type:'POST',
        dataType:'json',
        success:function(response){
            //invoke when it success
            console.log(response);
            if(response.status=="created"){
                // open payment form
                let options = {
                    key:"",
                    amount: response.amount,
                    currency:"INR",
                    name: "Smart Contact Hub",
                    description:"Donation",
                    image:"https://i.pinimg.com/550x/9d/dd/50/9ddd50ff3f173ef56a61a7277b50dcbd.jpg",
                    order_id:response.id,
                    handler:function(response){
                        console.log(response.razorpay_payment_id)
                        console.log(response.razorpay_order_id)
                        console.log(response.razorpay_signature)
                        console.log("payment successful...")
//                      alert("Congrates !! success")

                        updatePaymentOnServer(
                        response.razorpay_payment_id,
                        response.razorpay_order_id,
                        "paid"
                        );

                        swal("Good Job!!","Payment successfull","success");
                    },
                    prefill: {
                        name: "",
                        email: "",
                        contact: "7489019625"
                        },
                        notes: {
                        address: "Razorpay Corporate Office"
                        
                        },
                        theme: {
                        color: "#3399cc"
                        },

                };
                let rzp = new Razorpay(options);

                rzp.on("payment.failed",function(response){
                    console.log(response.error.code);
                    console.log(response.error.description);
                    console.log(response.error.source);
                    console.log(response.error.step);
                    console.log(response.error.reason);
                    console.log(response.error.metadata.order_id);
                    console.log(response.error.metadata.payment_id);
//                    alert("Oops payment failed");
                      swal("Failed !!","Payment Failed","error");
                })
                rzp.open();
            }
        },
        error:function(error){
            console.log(error);
            alert("something went wrong !!");
        }
    }
)
};

// using ajax to create a order value on our server

function updatePaymentOnServer(payment_id, order_id, status)
{
    $.ajax({
            url:'/user/update_order',
            data:JSON.stringify({
            payment_id:payment_id,
            order_id:order_id,
            status: status,
            }),
            contentType:'application/json',
            type:'POST',
            dataType:'json',
            success:function(response){
            swal("Good Job!!","Payment successfull","success");
            },
            error:function(error){
            swal("Failed !!","your payment is successfull, but we did not got on server","error");
            }
    });
};

