    "use strict";
    let btnSave = document.getElementById("btnSave");
    let btnClose = document.getElementById("btnClose");


    document.addEventListener('DOMContentLoaded', function() {
        addEventListenerCrudBtn();
    });


    function addEventListenerCrudBtn(){
        btnSave.addEventListener('click', fnBtnSave);
        btnClose.addEventListener('click', fnBtnClose);
    }


/* ===================== 유효성 체크 ===================== */

    function fnChkValid(){
        let title = document.getElementById("writeTitle").value.trim();         // 글 제목
        let content = document.getElementById("writeContent").value.trim();     // 글 내용

    // 제목
        if(title ===""){
            alert("제목을 입력하세요.");
            document.getElementById("writeTitle").focus();
            return false;
        }

    // 내용
        if(content ===""){
            alert("내용을 입력하세요.");
            document.getElementById("writeContent").focus();
            return false;
        }

        return true;
    }

/* 저장 버튼 */
    function fnBtnSave() {
        // 유효성 검사
        if (!fnChkValid()) {
            return;
        }

        let title = document.getElementById("writeTitle").value;      // 글 제목
        let content = document.getElementById("writeContent").value;  // 글 내용
        let fileUpload = document.getElementById("fileUpload");       // 파일 업로드
        let selectedFile = fileUpload.files[0];                       // 업로드한 파일


        // 텍스트 데이터 설정
        let data = {
            title: title,
            comment: content,
        };

        // 텍스트 저장 요청
        fetch('/boardInsert', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(response => {
            console.log('Success:', response);
            console.log("fileUpload:", fileUpload);
            console.log("fileUpload.files:", fileUpload.files);
            console.log("fileUpload.files.length:", fileUpload.files.length);
            console.log("selectedFile:", selectedFile);

            alert('저장이 완료되었습니다.');

            // 파일이 있는 경우에만 업로드
            if (selectedFile) {
                let formData = new FormData();
                formData.append("file", selectedFile);
                formData.append("oriName", selectedFile.name);
                formData.append("no", response.no); // 게시글 번호 포함!

                console.log(`file: ${selectedFile}`);
                console.log(`oriName: ${selectedFile.name}`);
                console.log(`no: ${response.no}`);
                console.log(formData);


                $.ajax({
                    url: '/boardUpload',
                    processData: false,
                    contentType: false,
                    data: formData,
                    type: 'POST',
/*                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },*/
                    success: function (result) {
                        console.log("파일 업로드 결과:", result);
                        window.location.href = "/";
                    },
                    error: function () {
                        alert("파일 업로드에 실패했습니다.");
                    }
                });
            } else {
                window.location.href = "/";
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('저장 중 오류가 발생했습니다.');
        });
    }

/* 닫기 버튼 */
    function fnBtnClose(){
        window.location.href = "/";
    }
