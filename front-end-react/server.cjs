// price_1QmMlVP2Nu3prt3DuEkfz5ir Aksesuars C
// price_1QmMlEP2Nu3prt3DURxCirnS Aksesuars B
// price_1QmMkzP2Nu3prt3DWsbmXaqu Aksesuars A

// price_1QmMklP2Nu3prt3D8WfdDuoJ Lelle C
// price_1QmMkWP2Nu3prt3DALbwJI5j Lelle B
// price_1QmMkGP2Nu3prt3DT44GuPSS Lelle A

// price_1QmMk2P2Nu3prt3DLUIPU9wN Salle C
// price_1QmMjmP2Nu3prt3DuUYMjmC5 Salle B
// price_1QmMjHP2Nu3prt3DefkFaurf Salle A

// price_1QmMixP2Nu3prt3D5NajK1eu Cepure C
// price_1QmMigP2Nu3prt3DcvFPiaKY Cepure B
// price_1QmMiQP2Nu3prt3DCjiKrF33 Cepure A

// price_1QmMi7P2Nu3prt3DFqRmTk3q Dzemperis C
// price_1QmMhrP2Nu3prt3Dc4fUcImD Dzemperis B
// price_1QmMheP2Nu3prt3D8OjMZ8Xl Dzemperis A

// price_1QmMhPP2Nu3prt3D5tUN5Lkc Zeķes pusaudžiem C
// price_1QmMgYP2Nu3prt3DaZkyTMOL Zeķes pusaudžiem B
// price_1QmMgFP2Nu3prt3DtjbDkRQQ Zeķes pusaudžiem A

// price_1QmMfeP2Nu3prt3DUUhzYj9s Adīti ziemas dūraiņi C
// price_1QmMfJP2Nu3prt3Datb8nG43 Adīti ziemas dūraiņi B
// price_1QmMexP2Nu3prt3D8rlEKDMh Adīti ziemas dūraiņi A

const express = require('express');
var cors = require('cors');
const stripe = require('stripe')('sk_test_51QhuKrP2Nu3prt3D44VMnxS9WpspILN4GAF8C9NtrSRYh8NF9ZWdYHj6Dsia5U46AESuWmSsGkohEVFZKuDCTaYr00eHCgVtN8');

const stripeProducts = [0, "price_1QmMexP2Nu3prt3D8rlEKDMh", "price_1QmMfJP2Nu3prt3Datb8nG43", "price_1QmMfeP2Nu3prt3DUUhzYj9s", "price_1QmMkzP2Nu3prt3DWsbmXaqu", "price_1QmMlEP2Nu3prt3DURxCirnS",
    "price_1QmMlVP2Nu3prt3DuEkfz5ir", "price_1QmMiQP2Nu3prt3DCjiKrF33", "price_1QmMigP2Nu3prt3DcvFPiaKY", "price_1QmMixP2Nu3prt3D5NajK1eu", "price_1QmMheP2Nu3prt3D8OjMZ8Xl", "price_1QmMhrP2Nu3prt3Dc4fUcImD",
    "price_1QmMi7P2Nu3prt3DFqRmTk3q", "price_1QmMkGP2Nu3prt3DT44GuPSS", "price_1QmMkWP2Nu3prt3DALbwJI5j", "price_1QmMklP2Nu3prt3D8WfdDuoJ", "price_1QmMjHP2Nu3prt3DefkFaurf",
    "price_1QmMjmP2Nu3prt3DuUYMjmC5", "price_1QmMk2P2Nu3prt3DLUIPU9wN", "price_1QmMgFP2Nu3prt3DtjbDkRQQ", "price_1QmMgYP2Nu3prt3DaZkyTMOL", "price_1QmMhPP2Nu3prt3D5tUN5Lkc"
]

const app = express();
app.use(cors());
app.use(express.static("public"));
app.use(express.json());

app.post("/samaksa", async(req, res) => {
    try{
        const items = req.body.items;
        let lineItems = [];
        items.forEach((item) => {
            lineItems.push(
                {
                    price: stripeProducts[item.id],
                    quantity: item.quantity
                }
            )
        });
    
        const session = await stripe.checkout.sessions.create({
            line_items: lineItems,
            mode: 'payment',
            success_url: "http://localhost:3000/success",
            cancel_url: "http://localhost:3000/cancel",
        });
    
        res.send(JSON.stringify({
            url: session.url
        }));
    }catch{
        res.send(JSON.stringify({
            success: false
        }));
    }
});

app.listen(4000, () => console.log("Listening"));