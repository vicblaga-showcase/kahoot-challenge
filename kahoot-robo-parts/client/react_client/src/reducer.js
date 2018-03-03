import {Map} from 'immutable';

const INITIAL_STATE = Map({
    component: 'list-all',
    urls: {
        parts: 'http://localhost:4567/parts'
    },
    parts: Map({
        isLoaded: false,
        data: []
    })
});

let reducer = (state = INITIAL_STATE, action) => {
    if (action.type === 'PARTS_HAVE_LOADED') {
        return state.setIn(["parts", "isLoaded"], true).setIn(["parts", "data"], action.payload.data);
    }

    if (action.type === 'DISPLAY_PART_FORM') {
        return state.set('component', 'add-part');
    }

    if (action.type === 'ADD_PART') {
        state.updateIn(['parts', 'data'], parts => parts.push(action.payload.data));
        return state;   
    }

    if (action.type === 'REDIRECT_TO_PARTS') {
        return state.set('component', 'list-all');
    }

    if (action.type === 'DELETE_PART') {
        let parts = state.get('parts').get('data');
        return state.setIn(['parts', 'data'], parts.filter(part => part.id !== action.payload));
    }

    if (action.type === 'DISPLAY_COMPATIBLE_PARTS') {
        return state.set('component', 'display-compatible').set('id', action.payload);
    }
    return state;
};

const loadParts = (url, dispatch) => {
    fetch(url)
    .then(response => response.json())
    .then((data) => {
        dispatch({type: 'PARTS_HAVE_LOADED', payload: data});
    }, (error) => {
        dispatch({type: 'PARTS_LOADING_ERROR', payload: error});
    });
};

const displayPartForm = () => {
    return {
        type: 'DISPLAY_PART_FORM'
    };
};

const addPart = (url, name, dispatch) => {
    let init = {
        method: 'POST',
        body: JSON.stringify({name})
    }
    fetch(url, init)
    .then(response => response.json())
    .then((data) => {
        dispatch({type: 'ADD_PART', payload: data});
        dispatch({type: 'REDIRECT_TO_PARTS'});
    }, (error) => {
        dispatch({type: 'ADD_PART_ERROR', payload: error})
    });
};

const finishEdit = (url, id, name, dispatch) => {
    let init = {
        method: 'PUT',
        body: JSON.stringify({id, name})
    };

    fetch(url, init)
    .then(response => response.json())
    .then((data) => {
        dispatch({type: 'UPDATE_PART', payload: {prev_id: id, now: data}});
        dispatch({type: 'REDIRECT_TO_PARTS'});
    }, (error) => {
        dispatch({type: 'ADD_PART_ERROR', payload: error})
    });
}

const deletePart = (url, id, dispatch) => {
    let deleteUrl = `${url}/${id}`;
    fetch(deleteUrl, {
        method: 'DELETE'
    })
    .then(response => response.json())
    .then((data) => {
        dispatch({type: 'DELETE_PART', payload: id});
        dispatch({type: 'REDIRECT_TO_PARTS'});
    }, (error) => {
        dispatch({type: 'ADD_PART_ERROR', payload: error});
    })
}

export {reducer, loadParts, displayPartForm, addPart, finishEdit, deletePart};