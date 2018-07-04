package rewards.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rewards.Dining;
import rewards.RewardConfirmation;
import rewards.RewardNetwork;

@Controller
public class RewardsController {

    private RewardNetwork rewardNetwork;

    @Autowired
    public RewardsController(RewardNetwork rewardNetwork) {
        this.rewardNetwork = rewardNetwork;
    }

    @RequestMapping("/newreward")
    public String newReward() {
        return "newreward";
    }

    @PostMapping("/rewards")
    public String doReward( @RequestParam String creditCardNumber,
                            @RequestParam String amount,
                            @RequestParam String merchantNumber,
                            RedirectAttributes model) {

        // prepare input into the application service
        Dining dining = Dining.createDining(amount, creditCardNumber, merchantNumber);

        // expose in "request scope" for other resources to access
        model.addFlashAttribute("dining", dining);

        try {
            // invoke application
            RewardConfirmation confirmation = rewardNetwork.rewardAccountFor(dining);
            // expose in "request scope" for other resources to access
            model.addFlashAttribute("rewardConfirmation", confirmation);
            return "redirect:rewardConfirmation";
        } catch (Exception e) {
            return "redirect:rewardError";
        }
    }
}
