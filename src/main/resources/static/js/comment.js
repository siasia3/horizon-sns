



document.getElementById('postDetailModal').addEventListener('input', function (event){
    //게시판 상세조회 댓글 게시버튼 활성화
    if(event.target && event.target.classList.contains('comment-content')){
        let commentPostBtn = event.target.nextElementSibling;
        if (event.target.value.trim() !== "") {
            commentPostBtn.style.display = 'block';
        } else {
            commentPostBtn.style.display = 'none'; // 버튼 비활성화
        }
    }
});

document.getElementById('postDetailModal').addEventListener('click', function (event){
    //게시판 상세조회 답글달기 클릭 이벤트
    if(event.target && event.target.classList.contains('comment-content')){
        let commentPostBtn = event.target.nextElementSibling;
        if (event.target.value.trim() !== "") {
            commentPostBtn.style.display = 'block';
        } else {
            commentPostBtn.style.display = 'none'; // 버튼 비활성화
        }
    }

    if (event.target && event.target.classList.contains('commentPostBtn')){
        //상세조회 댓글 등록
        const targetPost = event.target.closest('.postDetail-modal-content');
        let commentContent = targetPost.querySelector('.comment-content').value;
        let postId = targetPost.getAttribute('data-post-id');
        let data = {
            postId: postId,
            commentContent: commentContent
        }
        if (commentContent.startsWith('@')){
            let parentId = document.getElementById('detailCommentInput').getAttribute('data-parent-id');
            data = {
                postId: postId,
                commentContent: commentContent,
                parentId: parentId
            }
        }
        createComment(data).then(result => renderComment(result));
        targetPost.querySelector('.comment-content').value = '';
    }

});


/*
function fetchDump(jsonData, optionsParam, renderFunc) {

    try {
        const options = {
            method: optionsParam.method ? optionsParam.method : 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonData,
        };

        fetchWithAuth('/api/comment',options).then(rep =>
            renderFunc(rep)
        );
    } catch () {

    }



}*/

//댓글 등록 API
async function createComment(comment){

    let jsonData = JSON.stringify(comment);


        const options = {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonData,
        };

        const result = await fetchWithAuth('/api/comment',options);
        let responseData =  await result.json();

        return responseData;
}

//게시판 댓글 페이징 조회 API
async function getComments(postId,size,page){

    const options = {
        method: 'get'
    };

    const response = await fetchWithAuth(`/api/post/${postId}/comments?page=${page}&size=${size}`, options);
    return await response.json();
}
//상세조회 모달 댓글 등록시 댓글 렌더링
function renderComment(comment){
    let postCommentArea = document.getElementById('comment-inner');
    let siblingComment = postCommentArea.querySelector('.comment');
    let commentClone = document.getElementById('postComment-template').content.cloneNode(true);
    commentClone.querySelector('.comment').setAttribute('data-comment-id',comment.commentId);
    commentClone.querySelector('.reply-area').remove();
    if(comment.authorProfileImage){
        commentClone.querySelector('.profile-img').src = comment.authorProfileImage;
    }
    commentClone.querySelector('.commentAuthor').innerText = comment.commentAuthor;
    commentClone.querySelector('.commentContent').insertAdjacentHTML('beforeend', comment.commentContent);
    postCommentArea.insertBefore(commentClone,siblingComment);

}



//상세조회시 여러개의 댓글 렌더링
function renderCommentElements(commentDatas){

    if(commentDatas.content){
        let postCommentArea = document.getElementById('comment-inner');
        let userId = sessionStorage.getItem("userId");
        commentDatas.content.forEach((comment,index) => {
            let postComment = document.getElementById('postComment-template').content.cloneNode(true);
            postComment.querySelector('.comment').setAttribute('data-comment-id',comment.commentId);
            //작성자가 프사 있는 경우
            if(comment.authorProfileImage) {
                postComment.querySelector('.profile-img').src = comment.authorProfileImage;
            }
            postComment.querySelector('.commentAuthor').innerText = comment.commentAuthor;
            postComment.querySelector('.commentContent').insertAdjacentHTML('beforeend', comment.commentContent);

            //댓글 작성자인지 아닌지
            if(comment.memberId != userId){
                postComment.querySelector('.commentDeleteBtn').remove();
            }
            //대댓글 있는 경우, 없는 경우 
            if(comment.replyCount > 0){
                postComment.querySelector('.replyCount').innerText = comment.replyCount;
            }
            if(comment.replyCount == 0){
                postComment.querySelector('.reply-area').remove();
            }
            postCommentArea.appendChild(postComment);
        });
        if(commentDatas.hasNext==true){
            document.getElementById('commentGet').style.display = 'block';
        }
        if(commentDatas.hasNext==false){
            document.getElementById('commentGet').style.display = 'none';
        }
    }


}
