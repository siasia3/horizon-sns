

//게시글 상세조회 좋아요 클릭이벤트
document.getElementById('postDetailModal').addEventListener('click', async(event) => {
    if (event.target && event.target.classList.contains('fa-heart')) {
        const targetPost = event.target.closest('.postDetail-modal-content');
        if(event.target.classList.contains('fa-regular')){
            let heartIcon = targetPost.querySelector('.fa-heart');
            let likeId = await likeIncreaseAPI(targetPost);
            if(likeId){
                likeIncreaseRender(targetPost,heartIcon,likeId);
            }
            return;
        }

        if(event.target.classList.contains('fa-solid')){
            let heartIcon = event.target;
            let likeId = await likeDecreaseAPI(heartIcon);
            if(likeId){
                likeDecreaseRender(targetPost,heartIcon);
            }
        }

    }
});


//좋아요 Insert API
async function likeIncreaseAPI(targetPost) {
    let postId = targetPost.getAttribute('data-post-id');
    let data = {
        postId: postId
    }
    let jsonData = JSON.stringify(data);
    try {
        const options = {
            noSpinner: true,
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonData,
            credentials: 'include'
        };
        const result = await fetchWithAuth('/api/like',options);
        let likeId = await result.json();
        return likeId;
    } catch (error) {
        console.error('오류 발생:', error);
        alert('오류가 발생했습니다.');
    }

}

//좋아요 Delete API
async function likeDecreaseAPI(targetLike){
    let likeId = targetLike.getAttribute('data-like-id');
    let data = {
        likeId: likeId
    }
    let jsonData = JSON.stringify(data);
    try {
        const options = {
            noSpinner: true,
            method: 'delete',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonData,
            credentials: 'include'
        };
        const result = await fetchWithAuth('/api/like',options);
        let likeId = await result.json();
        return likeId;
    } catch (error) {
            console.error('오류 발생:', error);
            alert('오류가 발생했습니다.');
    }
}

//좋아요 insert UI 변경
function likeIncreaseRender(postDiv,targetHeart,likeId){
    targetHeart.setAttribute('data-like-id',likeId);
    targetHeart.classList.remove('fa-regular');
    targetHeart.classList.add('fa-solid');
    let heartCnt = postDiv.querySelector('.heartCount').textContent;
    postDiv.querySelector('.heartCount').textContent = parseInt(heartCnt)+1;

}

//좋아요 delete UI 변경

function likeDecreaseRender(postDiv,targetHeart){
    targetHeart.classList.remove('fa-solid');
    targetHeart.classList.add('fa-regular');
    targetHeart.removeAttribute('data-like-id');
    let heartCnt = postDiv.querySelector('.heartCount').textContent;
    postDiv.querySelector('.heartCount').textContent = parseInt(heartCnt)-1;

}