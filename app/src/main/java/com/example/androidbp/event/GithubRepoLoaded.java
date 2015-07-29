package com.example.androidbp.event;

import com.example.androidbp.api.model.Repo;

import java.util.List;

/**
 * Created by sharp on 7/29/2015 AD.
 */
public class GithubRepoLoaded {
    private List<Repo> repos;

    public GithubRepoLoaded(List<Repo> repos) {

        this.repos = repos;
    }

    public List<Repo> getRepos() {
        return repos;
    }
}
