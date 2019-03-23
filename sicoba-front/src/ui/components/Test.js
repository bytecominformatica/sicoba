import React from 'react';
import {BehaviorSubject, combineLatest, timer} from 'rxjs';
import withObservableStream from "../../hocs/withObservableStream";
import { flatMap, map, debounce, filter } from 'rxjs/operators';

const SUBJECT = {
    POPULARITY: 'search',
    DATE: 'search_by_date',
};

const Test = ({
                  query = '',
                  subject,
                  stories = [],
                  onChangeQuery,
                  onSelectSubject,
              }) => (
    <div>
        <h1>React with RxJS</h1>

        <input
            type="text"
            value={query}
            onChange={event => onChangeQuery(event.target.value)}
        />

        <div>
            {Object.values(SUBJECT).map(value => (
                <button
                    key={value}
                    onClick={() => onSelectSubject(value)}
                    type="button"
                >
                    {value}
                </button>
            ))}
        </div>

        <p>{`http://hn.algolia.com/api/v1/${subject}?query=${query}`}</p>
        <ul>
            {stories.map(story => (
                <li key={story.objectID}>
                    <a href={story.url || story.story_url}>
                        {story.title || story.story_title}
                    </a>
                </li>
            ))}
        </ul>
    </div>
);


const query$ = new BehaviorSubject('react');
const subject$ = new BehaviorSubject(SUBJECT.POPULARITY);

const queryForFetch$ = query$.pipe(
    debounce(() => timer(1000)),
    filter(query => query !== ''),
);
const fetch$ = combineLatest(subject$, queryForFetch$).pipe(
    flatMap(([subject, query]) =>
        fetch(`https://hn.algolia.com/api/v1/${subject}?query=${query}`).then(response => response.json()),
    ),
    map(result => {
        console.log('result', result);
        return result.hits;
    }),
);

export default withObservableStream(
    combineLatest(subject$, query$, fetch$, (subject, query, stories) => ({
        subject,
        query,
        stories,
    })),
    {
        onChangeQuery: value => query$.next(value),
        onSelectSubject: subject => subject$.next(subject),
    },
    {
        query: '',
    }
)(Test);