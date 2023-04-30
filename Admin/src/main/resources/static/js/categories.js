// Ký hiệu 	$(" ")thường được sử dụng làm từ viết tắt cho hàm jquery selector.
$('document').ready(function(){
    $('table #editButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        // $.get tải dữ liệu từ Server bởi sử dụng một GET HTTP request.
        $.get(href, function (category, status){
            $('#idEdit').val(category.id); /*val dùng gán giá trị cho thành phần*/
            $('#nameEdit').val(category.name);
        });
        $('#editModal').modal();
    });

});