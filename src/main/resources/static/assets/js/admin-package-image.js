function packageSubmit(){
    let image1 = $('#image1').val();
    let image2 = $('#image2').val();

    if(image1==""||image1==null) {
        alert("상품 이미지를 확인해주세요");
        return false;
    }else if(image2==""||image2==null){
        alert("상품 상세이미지를 확인해주세요");
        return false;
    }
}
