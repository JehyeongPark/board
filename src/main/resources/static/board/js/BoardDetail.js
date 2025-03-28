    "use strict";
    let btnClose = document.getElementById("btnClose");
    let btnUpdate = document.getElementById("btnUpdate");
    // 수정 버튼 변경 여부 확인용 기존 데이터
    let originalData = {
        title: document.getElementById("writeTitle").value,
        content: document.getElementById("writeContent").value
    };
    let btnDelete = document.getElementById("btnDelete");
    let btnFileDownload = document.getElementById("fileDownload");


    document.addEventListener('DOMContentLoaded', function() {
        addEventListenerCrudBtn();
    });


    function addEventListenerCrudBtn(){
        btnClose.addEventListener('click', fnBtnClose);
        btnUpdate.addEventListener('click', fnBtnUpdate);
        btnDelete.addEventListener('click', fnBtnDelete);
        btnFileDownload.addEventListener('click', fnFileDownload);
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

/* 닫기 버튼 */
    function fnBtnClose(){
        window.close();
    }

/* 수정 버튼 */
    function fnBtnUpdate(){
        // 유효성 검사
        if(!fnChkValid()){
            return;
        }

        let no = document.getElementById("No").value;                 // 글 번호
        let title = document.getElementById("writeTitle").value;      // 글 제목
        let content = document.getElementById("writeContent").value;  // 글 내용

        // 변경 여부 확인
        if (originalData.title === title && originalData.content === content) {
            alert("변경된 내용이 없습니다.");
            return;
        }

        // 데이터 설정
        let data = {
            no : no,
            title: title,
            comment: content
        };

        // AJAX 요청
        fetch('/boardUpdate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(result => {
            console.log('Success:', result);
            alert('수정이 완료되었습니다.');
            if (window.opener) { // 부모 창이 존재하면
                window.opener.location.reload(); // 부모 창 새로고침
            }
            window.close()  // 팝업창 종료
        })
        .catch(error => {
            console.error('Error:', error);
            alert('저장 중 오류가 발생했습니다.');
        });
    }

/* 삭제 버튼 */
    function fnBtnDelete(){

    if(!confirm("정말 삭제하시겠습니까?")) {
        return;
    }

     let no = document.getElementById("No").value;  // 글 번호

     // 데이터 설정
         let data = {
             no : no
         };

        // AJAX 요청
        fetch('/boardDelete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(result => {
            console.log('Success:', result);

            alert('삭제 되었습니다.');
            if (window.opener) { // 부모 창이 존재하면
                window.opener.location.reload(); // 부모 창 새로고침
            }
            window.close()  // 팝업창 종료
        })
        .catch(error => {
            console.error('Error:', error);
            alert('저장 중 오류가 발생했습니다.');
        });
    }

/* 파일 다운로드 */
function fnFileDownload() {
    let fileNo = btnFileDownload.getAttribute("data-fno");

    fetch('/boardFileDownload?fno=' + fileNo, {
        method: 'GET'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        // Content-Disposition 헤더에서 파일명 추출
        const disposition = response.headers.get("Content-Disposition");
        let filename = "downloaded_file";
        if (disposition && disposition.includes("filename=")) {
            const match = disposition.match(/filename="?([^"]+)"?/);
            if (match.length > 1) {
                filename = decodeURIComponent(match[1]);
            }
        }

        return response.blob().then(blob => ({ blob, filename }));
    })
    .then(({ blob, filename }) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        a.remove();
        window.URL.revokeObjectURL(url);
    })
    .catch(error => {
        console.error('Download error:', error);
        alert('파일 다운로드 중 오류가 발생했습니다.');
    });
}