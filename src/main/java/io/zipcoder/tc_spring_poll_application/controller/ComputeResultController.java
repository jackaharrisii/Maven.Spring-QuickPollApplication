package io.zipcoder.tc_spring_poll_application.controller;

import io.zipcoder.tc_spring_poll_application.dtos.OptionCount;
import io.zipcoder.tc_spring_poll_application.dtos.VoteResult;
import io.zipcoder.tc_spring_poll_application.domain.Option;
import io.zipcoder.tc_spring_poll_application.domain.Vote;
import io.zipcoder.tc_spring_poll_application.repositories.PollRepository;
import io.zipcoder.tc_spring_poll_application.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
public class ComputeResultController {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private PollRepository pollRepository;

    @RequestMapping(value = "/computeresult", method = RequestMethod.GET)
    public ResponseEntity<?> computeResult(@RequestParam Long pollId) {
        VoteResult voteResult = new VoteResult();
        Iterable<Vote> allVotes = voteRepository.findVotesByPoll(pollId);

        //TODO: Implement algorithm to count votes
        Collection<OptionCount> optionCounts = new ArrayList<>();
        for (Option option : pollRepository.findOne(pollId).getOptions()){
            int count = 0;
            for(Vote v : allVotes){
                if (v.getOption().equals(option)) count++;
            }
            OptionCount optionCount = new OptionCount(option.getId(), count);
            optionCounts.add(optionCount);
        }
        voteResult.setResults(optionCounts);
        return new ResponseEntity<VoteResult>(voteResult, HttpStatus.OK);
    }
}
